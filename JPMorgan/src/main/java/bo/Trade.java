/**
 * 
 */
package main.java.bo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import main.java.resources.TradeType;

/**
 * @author mohit
 * 
 * Trade Business Object
 *
 */
public class Trade {

	String stockSymbol;			// Contains the stock symbol of the stock being traded
	BigDecimal quantity;		// Contains quantity of stock traded
	Timestamp tradeTimeStamp;	// Contains timestamp value of trade
	TradeType tradeTypeObj;		// Contains the type of trade, i.e., Buy or Sell
	BigDecimal tradePrice;		// Contains the stock price at which the trade occured
	
	/**
	 * Parameterised Constructor
	 * 
	 * @param stockSymbolLocal
	 * @param quantityLocal
	 * @param tradeTimeStampLocal
	 * @param tradeTypeLocal
	 * @param stockPriceLocal
	 */
	public Trade(String stockSymbolLocal, BigDecimal quantityLocal, Timestamp tradeTimeStampLocal, TradeType tradeTypeLocal,
			BigDecimal stockPriceLocal) {
		
		stockSymbol = stockSymbolLocal;
		quantity = quantityLocal;
		tradeTimeStamp = tradeTimeStampLocal;
		tradeTypeObj = tradeTypeLocal;
		tradePrice = stockPriceLocal;
		
	}

	/**
	 * Constructore calling super class 
	 */
	public Trade() {
		super();
	}
	
	/**
	 * overloaded equals method
	 * 
	 * @param testTrade
	 * @return
	 */
	public boolean equals(Trade testTrade){
		if (!(testTrade instanceof Trade)) {
	        return false;
	    }

		Trade that = testTrade;

	    // Custom equality check here.
	    return (this.stockSymbol.equals(that.stockSymbol)
	    		&& this.quantity.equals(that.quantity)
	    		&& this.tradeTimeStamp.equals(that.tradeTimeStamp)
	    		&& this.tradeTypeObj.equals(that.tradeTypeObj)
	    		&& this.tradePrice.equals(that.tradePrice));
	}

	/**
	 * @return the stockSymbol
	 */
	public String getStockSymbol() {
		return stockSymbol;
	}

	/**
	 * @param stockSymbol the stockSymbol to set
	 */
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the tradeTimeStamp
	 */
	public Timestamp getTradeTimeStamp() {
		return tradeTimeStamp;
	}

	/**
	 * @param tradeTimeStamp the tradeTimeStamp to set
	 */
	public void setTradeTimeStamp(Timestamp tradeTimeStamp) {
		this.tradeTimeStamp = tradeTimeStamp;
	}

	/**
	 * @return the tradeTypeObj
	 */
	public TradeType getTradeTypeObj() {
		return tradeTypeObj;
	}

	/**
	 * @param tradeTypeObj the tradeTypeObj to set
	 */
	public void setTradeTypeObj(TradeType tradeTypeObj) {
		this.tradeTypeObj = tradeTypeObj;
	}

	/**
	 * @return the stockPrice
	 */
	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	/**
	 * @param stockPrice the stockPrice to set
	 */
	public void setTradePrice(BigDecimal stockPrice) {
		this.tradePrice = stockPrice;
	}
	
	

}
