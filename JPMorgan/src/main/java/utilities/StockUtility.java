/**
 * 
 */
package main.java.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;


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

			/*
			 * Parameter error check
			 */

			if (stockList == null || stockList.isEmpty()) {
				return ErrorStrings.stockListError;
			}

			if (stockPrice == null || stockPrice.equals(BigDecimal.ZERO) || stockPrice.compareTo(BigDecimal.ZERO) < 0) {
				return ErrorStrings.stockPriceError;
			}

			if (stockSymbol == null) {
				return ErrorStrings.stockSymbolError;
			}

			if (stockFunction == null || (stockFunction != StockFunctionType.DIVIDEND_YIELD
					&& stockFunction != StockFunctionType.PE_RATIO)) {
				return ErrorStrings.stockFunctionFlagError;
			}

			Iterator<Stock> iterator = stockList.listIterator();
			Stock tempStock = new Stock();

			while (iterator.hasNext()) {
				tempStock = iterator.next();

				if (tempStock.getStockSymbol() == null) {
					return ErrorStrings.stockSymbolError;
				}

				if (tempStock.getStockSymbol().equals(stockSymbol)) {

					/*
					 * PE Ratio Calculation
					 */
					if (stockFunction.equals(StockFunctionType.PE_RATIO)) {

						if (tempStock.getLastDividend() == null || tempStock.getLastDividend().equals(BigDecimal.ZERO)
								|| tempStock.getLastDividend().compareTo(BigDecimal.ZERO) < 0) {
							return ErrorStrings.lastDividendError;
						}

						return stockPrice.divide(tempStock.getLastDividend(), 20, BigDecimal.ROUND_HALF_UP).toString();
					} else {

						/*
						 * Dividend Yield Calculation
						 */

						if (tempStock.getStockTypeObj() == null || (tempStock.getStockTypeObj() != StockType.COMMON
								&& tempStock.getStockTypeObj() != StockType.PREFERRED)) {
							return ErrorStrings.stockTypeFlagError;
						}

						if (tempStock.getStockTypeObj().equals(StockType.COMMON)) {

							if (tempStock.getLastDividend() == null
									|| tempStock.getLastDividend().compareTo(BigDecimal.ZERO) < 0) {
								return ErrorStrings.lastDividendError;
							}

							return tempStock.getLastDividend().divide(stockPrice, 20, BigDecimal.ROUND_HALF_UP);
						} else {

							if (tempStock.getFixedDividend() == null
									|| tempStock.getFixedDividend().compareTo(BigDecimal.ZERO) < 0) {
								return ErrorStrings.fixedDividendError;
							}

							if (tempStock.getParValue() == null
									|| tempStock.getParValue().compareTo(BigDecimal.ZERO) < 0) {
								return ErrorStrings.parValueError;
							}
							return tempStock.getFixedDividend().multiply(tempStock.getParValue().divide(
									BigDecimal.valueOf(100).multiply(stockPrice), 20, BigDecimal.ROUND_HALF_UP));
						}
					}
				}
			}
			return ErrorStrings.stockSymbolNotFound;

		} catch (NullPointerException e) {
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
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

			/*
			 * Parameter Error Checks
			 */

			if (stockSymbol == null || stockSymbol.equals(null)) {
				return ErrorStrings.stockSymbolError;
			}

			if (quantity == null || quantity.equals(BigDecimal.ZERO) || quantity.compareTo(BigDecimal.ZERO) < 0) {
				return ErrorStrings.tradeQuantityError;
			}

			if (currentTime == null || currentTime.getTime() <= 0) {
				return ErrorStrings.currentTimeError;
			}

			if (typeOfTrade == null || (typeOfTrade != TradeType.BUY && typeOfTrade != TradeType.SELL)) {
				return ErrorStrings.tradeTypeFlagError;
			}

			if (tradePrice == null || tradePrice.equals(BigDecimal.ZERO) || tradePrice.compareTo(BigDecimal.ZERO) < 0) {
				return ErrorStrings.tradePriceError;
			}

			if (tradeList == null) {
				return ErrorStrings.tradeListError;
			}

			Trade newTradeRecord = new Trade(stockSymbol, quantity, currentTime, typeOfTrade, tradePrice);
			Boolean duplicateFlag = new Boolean(false);

			Iterator<Trade> iterator = tradeList.listIterator();

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
				return ErrorStrings.duplicateFoundError;
			}

			tradeList.add(newTradeRecord);
			return tradeList;

		} catch (NullPointerException e) {
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
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

		try {
			if (stockSymbol == null || stockSymbol.equals(null)) {

				return ErrorStrings.stockSymbolError;
			}
			if (volumeWeightTypeObj == null || (volumeWeightTypeObj != VolumeWeightType.FIVE_MINUTES
					&& volumeWeightTypeObj != VolumeWeightType.TOTAL)) {

				return ErrorStrings.volumeWeightFlagError;
			}
			if ((currentTime == null || currentTime.getTime() <= 0) && volumeWeightTypeObj != VolumeWeightType.TOTAL) {

				return ErrorStrings.currentTimeError;
			}
			if (tradeList == null || tradeList.isEmpty()) {

				return ErrorStrings.tradeListError;
			}

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

			while (iterator.hasNext()) {
				tempTrade = iterator.next();

				if (tempTrade.getStockSymbol() == null || tempTrade.getStockSymbol().equals(null)) {

					return ErrorStrings.stockSymbolError;
				}

				if (tempTrade.getStockSymbol().equals(stockSymbol)) {

					if (volumeWeightTypeObj.equals(VolumeWeightType.FIVE_MINUTES)) {

						if (tempTrade.getTradeTimeStamp() == null || tempTrade.getTradeTimeStamp().getTime() <= 0) {

							return ErrorStrings.tradeTimeError;
						}

						if (tempTrade.getTradeTimeStamp().before(currentTime)
								&& tempTrade.getTradeTimeStamp().after(oldTime)) {

							if (tempTrade.getQuantity() == null || tempTrade.getQuantity().equals(BigDecimal.ZERO)
									|| tempTrade.getQuantity().compareTo(BigDecimal.ZERO) < 0) {

								return ErrorStrings.tradeQuantityError;
							}

							if (tempTrade.getTradePrice() == null || tempTrade.getTradePrice().equals(BigDecimal.ZERO)
									|| tempTrade.getTradePrice().compareTo(BigDecimal.ZERO) < 0) {

								return ErrorStrings.tradePriceError;
							}

							totalTradePriceQuantity = totalTradePriceQuantity
									.add(tempTrade.getTradePrice().multiply(tempTrade.getQuantity()));
							totalQuantity = totalQuantity.add(tempTrade.getQuantity());
							dataFoundFlag = true;
						}
					}
					if (volumeWeightTypeObj.equals(VolumeWeightType.TOTAL)) {

						totalTradePriceQuantity = totalTradePriceQuantity
								.add(tempTrade.getTradePrice().multiply(tempTrade.getQuantity()));

						totalQuantity = totalQuantity.add(tempTrade.getQuantity());
						dataFoundFlag = true;
					}
				}
			}

			if (dataFoundFlag) {

				totalTradePriceQuantity = totalTradePriceQuantity.divide(totalQuantity, 20, BigDecimal.ROUND_HALF_UP);
				return totalTradePriceQuantity;
			} else {
				return ErrorStrings.noRelevantTradesFound;
			}

		} catch (NullPointerException e) {
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
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

		try {
			if (stockList == null || stockList.equals(null) || stockList.isEmpty()) {

				return ErrorStrings.stockListError;
			}

			if (tradeList == null || tradeList.equals(null) || tradeList.isEmpty()) {

				return ErrorStrings.tradeListError;
			}

			Iterator<Stock> stockIterator = stockList.listIterator();
			Stock tempStock = new Stock();
			BigDecimal geometricMean = new BigDecimal(1);
			BigDecimal tempDecimal = new BigDecimal(0);

			while (stockIterator.hasNext()) {
				tempStock = stockIterator.next();

				if (tempStock.getStockSymbol() == null || tempStock.getStockSymbol().equals(null)) {
					return ErrorStrings.stockSymbolError;
				}

				tempDecimal = (BigDecimal) volumeWeightedStockPrice(tempStock.getStockSymbol(), null, tradeList,
						VolumeWeightType.TOTAL);

				geometricMean = geometricMean.multiply(tempDecimal);
			}

			geometricMean = ((BigDecimal) root(10, geometricMean)).setScale(20, BigDecimal.ROUND_HALF_UP);
			return geometricMean;

		} catch (NullPointerException e) {
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
			return ErrorStrings.unknownException;
		}
	}

	/**
	 * @param nthRoot
	 * @param targetValue
	 * @return
	 */
	static private Object root(final int nthRoot, final BigDecimal targetValue) {
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
			return ErrorStrings.nullPointer;
		} catch (ArithmeticException e) {
			return ErrorStrings.arithmeticException;
		} catch (Exception e) {
			return ErrorStrings.unknownException;
		}
	}
}
