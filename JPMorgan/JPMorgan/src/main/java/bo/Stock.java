package main.java.bo;

import java.math.BigDecimal;

import main.java.resources.StockType;

/**
 * @author mohit
 * 
 * Stock Business Object
 *
 */
/**
 * @author mohit
 *
 */
/**
 * @author mohit
 *
 */
public class Stock {

	String stockSymbol;			// Contains the Stock Symbol
	StockType stockTypeObj;		// Contains type of stock, i.e., Preferred or Common
	BigDecimal lastDividend;	// Contains value of Last Dividend received for stock
	BigDecimal fixedDividend;	// Contains value of Fixed Dividend received for stock
	BigDecimal parValue;		// Contains Par Value of stock

	/**
	 * Parameterised Constructor
	 * 
	 * @param stockSymbolLocal
	 * @param stockTypeLocal
	 * @param lastDividendLocal
	 * @param fixedDividendLocal
	 * @param parValueLocal
	 */
	public Stock(String stockSymbolLocal, StockType stockTypeLocal, BigDecimal lastDividendLocal, BigDecimal fixedDividendLocal,
			BigDecimal parValueLocal) {
		
		stockSymbol = stockSymbolLocal;
		stockTypeObj = stockTypeLocal;
		lastDividend = lastDividendLocal;
		fixedDividend = fixedDividendLocal;
		parValue = parValueLocal;
	}
	
	
	/**
	 * Constructor calling super class
	 */
	public Stock() {
		super();
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
	 * @return the stockTypeObj
	 */
	public StockType getStockTypeObj() {
		return stockTypeObj;
	}

	/**
	 * @param stockTypeObj the stockTypeObj to set
	 */
	public void setStockTypeObj(StockType stockTypeObj) {
		this.stockTypeObj = stockTypeObj;
	}

	/**
	 * @return the lastDividend
	 */
	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	/**
	 * @param lastDividend the lastDividend to set
	 */
	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	/**
	 * @return the fixedDividend
	 */
	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * @param fixedDividend the fixedDividend to set
	 */
	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	/**
	 * @return the parValue
	 */
	public BigDecimal getParValue() {
		return parValue;
	}

	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}
	
	
}
