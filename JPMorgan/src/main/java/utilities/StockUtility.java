/**
 * 
 */
package main.java.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.bo.Stock;
import main.java.bo.Trade;
import main.java.resources.ErrorStrings;
import main.java.resources.StockFunctionType;
import main.java.resources.StockType;
import main.java.resources.TradeType;
import main.java.resources.VolumeWeightType;

/**
 * @author mohit
 *
 */
public final class StockUtility {
	
	private static final Logger logger = LogManager.getLogger(StockUtility.class);
	
	/**
	 * 
	 */
	private StockUtility() {
			super();
	}

	/**
	 * Calculate Dividend Yield or P/E Ratio for a given price
	 * 
	 * @param stockPrice
	 * @param stockSymbol
	 * @param stockList
	 * @param stockFunction
	 * @return
	 */
	public static Object dividendYieldOrPERatio(BigDecimal stockPrice, String stockSymbol, List<Stock> stockList,
			StockFunctionType stockFunction) {
			
		try {
			logger.debug("Entering dividendYieldOrPERatio.");
			
			logger.debug("Key Values:-  Stock Price: "+stockPrice+" | Stock Symbol: "+stockSymbol+" | Stock List Object: "+stockList+" | Stock Function: "+stockFunction+"  |||");
			
			logger.debug("Primary error check started");
			/*
			 * Parameter error check
			 */

			if (stockList == null || stockList.isEmpty()) {
				logger.error(ErrorStrings.stockListError+" | null stockList = ",stockList);
				return ErrorStrings.stockListError;
			}

			if (stockPrice == null || stockPrice.equals(BigDecimal.ZERO) || stockPrice.compareTo(BigDecimal.ZERO) < 0) {
				logger.error(ErrorStrings.stockPriceError+" | null, 0 or -ve stockPrice = ",stockPrice);
				return ErrorStrings.stockPriceError;
			}

			if (stockSymbol == null) {
				logger.error(ErrorStrings.stockSymbolError+" | null stockSymbol = ",stockSymbol);
				return ErrorStrings.stockSymbolError;
			}

			if (stockFunction == null || (stockFunction != StockFunctionType.DIVIDEND_YIELD
					&& stockFunction != StockFunctionType.PE_RATIO)) {
				logger.error(ErrorStrings.stockFunctionFlagError+" | null or wrong stockFunction = ",stockFunction);
				return ErrorStrings.stockFunctionFlagError;
			}
			
			logger.debug("Primary error check ended");

			Iterator<Stock> iterator = stockList.listIterator();
			Stock tempStock = new Stock();
			BigDecimal returnThis = new BigDecimal(0);

			while (iterator.hasNext()) {
				tempStock = iterator.next();

				if (tempStock.getStockSymbol() == null) {
					logger.error(ErrorStrings.stockSymbolError+" | null stockSymbol = ",tempStock.getStockSymbol());
					return ErrorStrings.stockSymbolError;
				}

				if (tempStock.getStockSymbol().equals(stockSymbol)) {

					/*
					 * PE Ratio Calculation
					 */
					if (stockFunction.equals(StockFunctionType.PE_RATIO)) {
						
						logger.debug("PE Ratio Calculation Started");

						if (tempStock.getLastDividend() == null || tempStock.getLastDividend().equals(BigDecimal.ZERO)
								|| tempStock.getLastDividend().compareTo(BigDecimal.ZERO) < 0) {
							logger.error(ErrorStrings.lastDividendError+" | null, 0 or -ve lastDividend = ",tempStock.getLastDividend());
							return ErrorStrings.lastDividendError;
						}
						returnThis = stockPrice.divide(tempStock.getLastDividend(), 20, BigDecimal.ROUND_HALF_UP); 
						
						logger.debug("PE Ratio Calculation Ended");
						logger.info("PE Ratio: "+returnThis);
						logger.debug("Successfully Exiting dividendYieldOrPERatio.");
						return returnThis;
					} else {
						
						logger.debug("Dividend Yield Calculation Started");

						/*
						 * Dividend Yield Calculation
						 */

						if (tempStock.getStockTypeObj() == null || (tempStock.getStockTypeObj() != StockType.COMMON
								&& tempStock.getStockTypeObj() != StockType.PREFERRED)) {
							logger.error(ErrorStrings.stockTypeFlagError+" | null or wrong stockType = ",tempStock.getStockTypeObj());
							return ErrorStrings.stockTypeFlagError;
						}

						if (tempStock.getStockTypeObj().equals(StockType.COMMON)) {
							
							logger.debug("Share Type: "+StockType.COMMON);

							if (tempStock.getLastDividend() == null
									|| tempStock.getLastDividend().compareTo(BigDecimal.ZERO) < 0) {
								logger.error(ErrorStrings.lastDividendError+" | null or -ve lastDividend = ",tempStock.getLastDividend());
								return ErrorStrings.lastDividendError;
							}
							
							returnThis = tempStock.getLastDividend().divide(stockPrice, 20, BigDecimal.ROUND_HALF_UP);
							
							logger.debug("Dividend Yield Calculation Ended");
							logger.info("Dividend Yield: "+returnThis);
							logger.debug("Successfully Exiting dividendYieldOrPERatio.");
							return returnThis;
						} else {
							
							logger.debug("Share Type: "+StockType.PREFERRED);

							if (tempStock.getFixedDividend() == null
									|| tempStock.getFixedDividend().compareTo(BigDecimal.ZERO) < 0) {
								logger.error(ErrorStrings.fixedDividendError+" | null or -ve fixedDividend = ",tempStock.getFixedDividend());
								return ErrorStrings.fixedDividendError;
							}

							if (tempStock.getParValue() == null
									|| tempStock.getParValue().compareTo(BigDecimal.ZERO) < 0) {
								logger.error(ErrorStrings.parValueError+" | null or -ve parValue = ",tempStock.getParValue());
								return ErrorStrings.parValueError;
							}
							returnThis = tempStock.getFixedDividend().multiply(tempStock.getParValue().divide(
									BigDecimal.valueOf(100).multiply(stockPrice), 20, BigDecimal.ROUND_HALF_UP));
							
							logger.debug("Dividend Yield Calculation Ended");
							logger.info("Dividend Yield: "+returnThis);
							logger.debug("Successfully Exiting dividendYieldOrPERatio.");
							return returnThis;
						}
					}
				}
			}
			logger.error(ErrorStrings.stockSymbolNotFound+" | Stock Symbol Not Found in List ",stockList);
			return ErrorStrings.stockSymbolNotFound;

		} catch (NullPointerException e) {
			logger.error(ErrorStrings.nullPointer,e);
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			logger.error(ErrorStrings.arithmeticException,e);
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
			logger.error(ErrorStrings.unknownException,e);
			return ErrorStrings.unknownException;
		}
	}

	/**
	 * Record incoming trade
	 * 
	 * @param stockSymbol
	 * @param quantity
	 * @param currentTime
	 * @param typeOfTrade
	 * @param tradePrice
	 * @param tradeList
	 * @return
	 */
	public static Object recordTrade(String stockSymbol, BigDecimal quantity, Timestamp currentTime,
			TradeType typeOfTrade, BigDecimal tradePrice, List<Trade> tradeList) {

		try {
			logger.debug("Entering recordTrade.");
			logger.debug("Key Values:-  Stock Symbol: "+stockSymbol+" | Quantity: "+quantity+" | Trade Price: "+tradePrice+" | Timestamp: "+currentTime+" | Type of Trade: "+typeOfTrade+" | Trade List Obj: "+tradeList+"  |||");
			logger.debug("Primary error check started");

			/*
			 * Parameter Error Checks
			 */

			if (stockSymbol == null || stockSymbol.equals(null)) {
				logger.error(ErrorStrings.stockSymbolError+" | null stockSymbol = ",stockSymbol);
				return ErrorStrings.stockSymbolError;
			}

			if (quantity == null || quantity.equals(BigDecimal.ZERO) || quantity.compareTo(BigDecimal.ZERO) < 0) {
				logger.error(ErrorStrings.tradeQuantityError+" | null, 0 or -ve quantity = ",quantity);
				return ErrorStrings.tradeQuantityError;
			}

			if (currentTime == null || currentTime.getTime() <= 0) {
				logger.error(ErrorStrings.currentTimeError+" | null or <= 0currentTime = ",currentTime);
				return ErrorStrings.currentTimeError;
			}

			if (typeOfTrade == null || (typeOfTrade != TradeType.BUY && typeOfTrade != TradeType.SELL)) {
				logger.error(ErrorStrings.tradeTypeFlagError+" | null or wrong typeOfTrade = ",typeOfTrade);
				return ErrorStrings.tradeTypeFlagError;
			}

			if (tradePrice == null || tradePrice.equals(BigDecimal.ZERO) || tradePrice.compareTo(BigDecimal.ZERO) < 0) {
				logger.error(ErrorStrings.tradePriceError+" | null, 0 or -ve tradePrice = ",tradePrice);
				return ErrorStrings.tradePriceError;
			}

			if (tradeList == null) {
				logger.error(ErrorStrings.tradeListError+" | null tradeList = ",tradeList);
				return ErrorStrings.tradeListError;
			}
			
			logger.debug("Primary error check ended");

			Trade newTradeRecord = new Trade(stockSymbol, quantity, currentTime, typeOfTrade, tradePrice);
			Boolean duplicateFlag = new Boolean(false);

			Iterator<Trade> iterator = tradeList.listIterator();
			
			logger.debug("Checking for Duplicates");
			/*
			 * Check for duplicates
			 */
			while (iterator.hasNext()) {

				if (iterator.next().equals(newTradeRecord)) {
					duplicateFlag = true;
					break;
				}
			}

			if (duplicateFlag) {
				logger.error(ErrorStrings.duplicateFoundError+" | Duplicate Recrod Found in List. Passed Record = ",newTradeRecord);
				return ErrorStrings.duplicateFoundError;
			}
			
			logger.debug("Error Check Ended");
			logger.info("Adding Trade Record:-  Stock Symbol: "+newTradeRecord.getStockSymbol()+" | Quantity: "+newTradeRecord.getQuantity()+" | Trade Price: "+newTradeRecord.getTradePrice()+" | Type of Trade: "+newTradeRecord.getTradeTypeObj()+" | Timestamp: "+newTradeRecord.getTradeTimeStamp()+" |||");
			logger.debug("Successfully Exiting recordTrade.");

			tradeList.add(newTradeRecord);
			return tradeList;

		} catch (NullPointerException e) {
			logger.error(ErrorStrings.nullPointer,e);
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			logger.error(ErrorStrings.arithmeticException,e);
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
			logger.error(ErrorStrings.unknownException,e);
			return ErrorStrings.unknownException;
		}
	}

	/**
	 * Calculate Volume Weighted Stock Price for a given Stock Symbol
	 * 
	 * @param stockSymbol
	 * @param currentTime
	 * @param tradeList
	 * @return
	 */
	public static Object volumeWeightedStockPrice(String stockSymbol, Timestamp currentTime, List<Trade> tradeList,
			VolumeWeightType volumeWeightTypeObj) {
			
		logger.debug("Entering volumeWeightedStockPrice");
		logger.debug("Key Values:-  Stock Symbol: "+stockSymbol+" | Timestamp: "+currentTime+" | Volume Weight Type: "+volumeWeightTypeObj+" | Trade List Obj: "+tradeList+"  |||");

		try {
			
			logger.debug("Primary error check started");
			
			if (stockSymbol == null || stockSymbol.equals(null)) {
				logger.error(ErrorStrings.stockSymbolError+" | null stockSymbol = ",stockSymbol);
				return ErrorStrings.stockSymbolError;
			}
			
			if (volumeWeightTypeObj == null || (volumeWeightTypeObj != VolumeWeightType.FIVE_MINUTES
					&& volumeWeightTypeObj != VolumeWeightType.TOTAL)) {
				logger.error(ErrorStrings.volumeWeightFlagError+" | null or wrong volumeWeightTypeObj = ",volumeWeightTypeObj);
				return ErrorStrings.volumeWeightFlagError;
			}
			
			//current time only comes into relevance when calculated Volume Weighted Stock Price for past 5 minutes. 
			if ((currentTime == null || currentTime.getTime() <= 0) && volumeWeightTypeObj != VolumeWeightType.TOTAL){
				logger.error(ErrorStrings.currentTimeError+" | null or <=0 currentTime = ",currentTime);
				return ErrorStrings.currentTimeError;
			}
			if (tradeList == null || tradeList.isEmpty()) {
				logger.error(ErrorStrings.tradeListError+" | null or empty tradeList = ",tradeList);
				return ErrorStrings.tradeListError;
			}
			
			logger.debug("Primary error check ended");

			BigDecimal totalTradePriceQuantity = new BigDecimal("0.0");
			BigDecimal totalQuantity = new BigDecimal("0.0");
			Timestamp oldTime = null;

			if (volumeWeightTypeObj.equals(VolumeWeightType.FIVE_MINUTES)) {
				oldTime = new Timestamp(currentTime.getTime() - 300001);
				currentTime = new Timestamp(currentTime.getTime() + 1);
			}

			Boolean dataFoundFlag = new Boolean(false);

			Iterator<Trade> iterator = tradeList.listIterator();
			Trade tempTrade = new Trade();
			
			logger.debug("Calculation loop started");

			while (iterator.hasNext()) {
				tempTrade = iterator.next();

				if (tempTrade.getStockSymbol() == null || tempTrade.getStockSymbol().equals(null)) {
					logger.error(ErrorStrings.stockSymbolError+" | null stockSymbol = ",tempTrade.getStockSymbol());
					return ErrorStrings.stockSymbolError;
				}

				if (tempTrade.getStockSymbol().equals(stockSymbol)) {

					if (volumeWeightTypeObj.equals(VolumeWeightType.FIVE_MINUTES)) {
						
						logger.debug("Step: Volume Weight Type: Last 5 Minutes");

						if (tempTrade.getTradeTimeStamp() == null || tempTrade.getTradeTimeStamp().getTime() <= 0) {
							logger.error(ErrorStrings.tradeTimeError+" | null or <=0 tradeTime = ",tempTrade.getTradeTimeStamp());
							return ErrorStrings.tradeTimeError;
						}

						if (tempTrade.getTradeTimeStamp().before(currentTime)
								&& tempTrade.getTradeTimeStamp().after(oldTime)) {

							if (tempTrade.getQuantity() == null || tempTrade.getQuantity().equals(BigDecimal.ZERO)
									|| tempTrade.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
								logger.error(ErrorStrings.tradeQuantityError+" | null, 0 or -ve tradeQuantity = ",tempTrade.getQuantity());
								return ErrorStrings.tradeQuantityError;
							}

							if (tempTrade.getTradePrice() == null || tempTrade.getTradePrice().equals(BigDecimal.ZERO)
									|| tempTrade.getTradePrice().compareTo(BigDecimal.ZERO) < 0) {
								logger.error(ErrorStrings.tradePriceError+" | null, 0  or -ve tradePrice = ",tempTrade.getTradePrice());
								return ErrorStrings.tradePriceError;
							}

							totalTradePriceQuantity = totalTradePriceQuantity
									.add(tempTrade.getTradePrice().multiply(tempTrade.getQuantity()));
							totalQuantity = totalQuantity.add(tempTrade.getQuantity());
							dataFoundFlag = true;
						}
					}
					if (volumeWeightTypeObj.equals(VolumeWeightType.TOTAL)) {
						
						logger.debug("Step: Volume Weight Type: Overall");

						totalTradePriceQuantity = totalTradePriceQuantity
								.add(tempTrade.getTradePrice().multiply(tempTrade.getQuantity()));

						totalQuantity = totalQuantity.add(tempTrade.getQuantity());
						dataFoundFlag = true;
					}
				}
			}

			if (dataFoundFlag) {
				
				totalTradePriceQuantity = totalTradePriceQuantity.divide(totalQuantity, 20, BigDecimal.ROUND_HALF_UP);
				logger.info("Volume Weight Stock Price: "+totalTradePriceQuantity);
				logger.debug("Successfully Exiting volumeWeightedStockPrice.");
				
				return totalTradePriceQuantity;
			} else {
				logger.error(ErrorStrings.noRelevantTradesFound+" | No Relevant Trade Found For Stock Symbol = ",stockSymbol);
				return ErrorStrings.noRelevantTradesFound;
			}

		} catch (NullPointerException e) {
			logger.error(ErrorStrings.nullPointer,e);
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			logger.error(ErrorStrings.arithmeticException,e);
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
			logger.error(ErrorStrings.unknownException,e);
			return ErrorStrings.unknownException;
		}
	}

	/**
	 * Calculate GBCE All Share Index Value
	 * 
	 * @param stockList
	 * @param tradeList
	 * @return
	 */
	public static Object geometricMean(List<Stock> stockList, List<Trade> tradeList) {
		
		logger.debug("Entering geometricMean");
		logger.debug("Values Recieved:-  Stock List Obj: "+stockList+" | Trade List Obj: "+tradeList+"  |||");

		try {
			
			logger.debug("Primary Error Check Started");
			
			if (stockList == null || stockList.equals(null) || stockList.isEmpty()) {
				logger.error(ErrorStrings.stockListError+" | null or empty stockList = ",stockList);
				return ErrorStrings.stockListError;
			}

			if (tradeList == null || tradeList.equals(null) || tradeList.isEmpty()) {
				logger.error(ErrorStrings.tradeListError+" | null or empty tradeList = ",tradeList);
				return ErrorStrings.tradeListError;
			}
			
			logger.debug("Primary Error Check Ended");

			Iterator<Stock> stockIterator = stockList.listIterator();
			Stock tempStock = new Stock();
			BigDecimal geometricMean = new BigDecimal(1);
			BigDecimal tempDecimal = new BigDecimal(0);
			
			logger.debug("Index Value Calculation Started");

			while (stockIterator.hasNext()) {
				tempStock = stockIterator.next();

				if (tempStock.getStockSymbol() == null || tempStock.getStockSymbol().equals(null)) {
					logger.error(ErrorStrings.stockSymbolError+" | null stockSymbol = ",tempStock.getStockSymbol());
					return ErrorStrings.stockSymbolError;
				}

				tempDecimal = (BigDecimal) volumeWeightedStockPrice(tempStock.getStockSymbol(), null, tradeList,
						VolumeWeightType.TOTAL);
				logger.debug("Stock: "+tempStock.getStockSymbol()+" | Total Volume Weight: "+tempDecimal+"  |||");
				geometricMean = geometricMean.multiply(tempDecimal);
			}

			geometricMean = ((BigDecimal) root(10, geometricMean)).setScale(20, BigDecimal.ROUND_HALF_UP);
			logger.debug("Index Value/Geometric Mean: "+geometricMean);
			logger.debug("Successfully Exiting geometricMean.");
			
			return geometricMean;

		} catch (NullPointerException e) {
			logger.error(ErrorStrings.nullPointer,e);
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			logger.error(ErrorStrings.arithmeticException,e);
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
			logger.error(ErrorStrings.unknownException,e);
			return ErrorStrings.unknownException;
		}
	}

	/**
	 * @param nthRoot
	 * @param targetValue
	 * @return
	 */
	static private Object root(final int nthRoot, final BigDecimal targetValue) {
		logger.debug("Entering Root Calculation");
		try{
		if (targetValue.compareTo(BigDecimal.ZERO) < 0) {
			throw new ArithmeticException("negative argument " + targetValue.toString() + " of root");
		}
		if (nthRoot <= 0) {
			throw new ArithmeticException("negative power " + nthRoot + " of root");
		}
		if (nthRoot == 1) {
			return targetValue;
		}

		BigDecimal s = new BigDecimal(Math.pow(targetValue.doubleValue(), 1.0 / nthRoot));
		final BigDecimal nth = new BigDecimal(nthRoot);
		final BigDecimal xhighpr = targetValue.setScale(2 + targetValue.scale());
		MathContext mc = new MathContext(2 + targetValue.precision());
		final double eps = targetValue.ulp().doubleValue() / (2 * nthRoot * targetValue.doubleValue());
		for (;;) {
			BigDecimal c = xhighpr.divide(s.pow(nthRoot - 1), mc);
			c = s.subtract(c);
			MathContext locmc = new MathContext(c.precision());
			c = c.divide(nth, locmc);
			s = s.subtract(c);
			if (Math.abs(c.doubleValue() / s.doubleValue()) < eps) {
				break;
			}
		}
		return s.round(new MathContext(1 + (int) (Math.log10(Math.abs(0.5 / eps)))));
		}catch (NullPointerException e) {
			logger.error(ErrorStrings.nullPointer,e);
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			logger.error(ErrorStrings.arithmeticException,e);
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
			logger.error(ErrorStrings.unknownException,e);
			return ErrorStrings.unknownException;
		}
	}
}
