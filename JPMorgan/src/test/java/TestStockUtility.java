package test.java;

import java.math.*;
import java.sql.*;
import java.util.*;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.bo.Stock;
import main.java.bo.Trade;
import main.java.resources.ErrorStrings;
import main.java.resources.StockFunctionType;
import main.java.resources.StockType;
import main.java.resources.TradeType;
import main.java.resources.VolumeWeightType;
import main.java.utilities.StockUtility;

/**
 * @author mohit
 *
 */
public class TestStockUtility {

	static List<Trade> tradeList;
	static List<Stock> stockList;
	private static final Logger logger = LogManager.getLogger(StockUtility.class);

	/*
	 * Populate Stock and Trade lists with random data
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		tradeList = new ArrayList<Trade>();
		stockList = new ArrayList<Stock>();

		Stock stockDetails = new Stock();
		Trade tradeDetails = new Trade();

		stockDetails = new Stock("TEA", StockType.COMMON, BigDecimal.valueOf(0), BigDecimal.valueOf(0),
				BigDecimal.valueOf(100));
		stockList.add(stockDetails);
		stockDetails = new Stock("POP", StockType.COMMON, BigDecimal.valueOf(8), BigDecimal.valueOf(0),
				BigDecimal.valueOf(100));
		stockList.add(stockDetails);
		stockDetails = new Stock("ALE", StockType.COMMON, BigDecimal.valueOf(23), BigDecimal.valueOf(0),
				BigDecimal.valueOf(60));
		stockList.add(stockDetails);
		stockDetails = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(2),
				BigDecimal.valueOf(100));
		stockList.add(stockDetails);
		stockDetails = new Stock("JOE", StockType.COMMON, BigDecimal.valueOf(13), BigDecimal.valueOf(0),
				BigDecimal.valueOf(250));
		stockList.add(stockDetails);
		stockDetails = new Stock("COFF", StockType.COMMON, BigDecimal.valueOf(1), BigDecimal.valueOf(13),
				BigDecimal.valueOf(140));
		stockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.valueOf(37), BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		stockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(35),
				BigDecimal.valueOf(30));
		stockList.add(stockDetails);
		stockDetails = new Stock("PEP", StockType.COMMON, BigDecimal.valueOf(21), BigDecimal.valueOf(7),
				BigDecimal.valueOf(80));
		stockList.add(stockDetails);
		stockDetails = new Stock("COK", StockType.COMMON, BigDecimal.valueOf(24), BigDecimal.valueOf(8),
				BigDecimal.valueOf(470));
		stockList.add(stockDetails);

		tradeDetails = new Trade("ALE", BigDecimal.valueOf(1727767), Timestamp.valueOf("2016-05-12 10:17:21.595"),
				TradeType.SELL, BigDecimal.valueOf(627));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(5973612), Timestamp.valueOf("2016-05-12 10:15:40.406"),
				TradeType.SELL, BigDecimal.valueOf(1163));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(1963444), Timestamp.valueOf("2016-05-12 10:16:11.471"),
				TradeType.SELL, BigDecimal.valueOf(1996));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(4619390), Timestamp.valueOf("2016-05-12 10:16:26.95"),
				TradeType.SELL, BigDecimal.valueOf(1418));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(3230468), Timestamp.valueOf("2016-05-12 10:16:53.987"),
				TradeType.SELL, BigDecimal.valueOf(800));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(7981112), Timestamp.valueOf("2016-05-12 10:18:23.575"),
				TradeType.SELL, BigDecimal.valueOf(94));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(5419655), Timestamp.valueOf("2016-05-12 10:20:59.736"),
				TradeType.SELL, BigDecimal.valueOf(844));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(563298), Timestamp.valueOf("2016-05-12 10:11:38.918"),
				TradeType.SELL, BigDecimal.valueOf(1859));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(6457256), Timestamp.valueOf("2016-05-12 10:15:37.90"),
				TradeType.BUY, BigDecimal.valueOf(530));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(1284783), Timestamp.valueOf("2016-05-12 10:15:15.920"),
				TradeType.BUY, BigDecimal.valueOf(340));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(282943), Timestamp.valueOf("2016-05-12 10:14:24.887"),
				TradeType.SELL, BigDecimal.valueOf(76));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(2074092), Timestamp.valueOf("2016-05-12 10:20:45.558"),
				TradeType.BUY, BigDecimal.valueOf(951));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(3761788), Timestamp.valueOf("2016-05-12 10:19:19.125"),
				TradeType.BUY, BigDecimal.valueOf(1948));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(1324715), Timestamp.valueOf("2016-05-12 10:15:55.728"),
				TradeType.SELL, BigDecimal.valueOf(1036));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(148297), Timestamp.valueOf("2016-05-12 10:10:58.431"),
				TradeType.SELL, BigDecimal.valueOf(1930));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(6808492), Timestamp.valueOf("2016-05-12 10:18:50.410"),
				TradeType.SELL, BigDecimal.valueOf(1062));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(2811584), Timestamp.valueOf("2016-05-12 10:10:45.842"),
				TradeType.SELL, BigDecimal.valueOf(554));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(4002849), Timestamp.valueOf("2016-05-12 10:15:8.283"),
				TradeType.SELL, BigDecimal.valueOf(1805));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(4341988), Timestamp.valueOf("2016-05-12 10:16:58.320"),
				TradeType.SELL, BigDecimal.valueOf(322));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(6026822), Timestamp.valueOf("2016-05-12 10:18:32.506"),
				TradeType.SELL, BigDecimal.valueOf(1141));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(6667852), Timestamp.valueOf("2016-05-12 10:20:2.812"),
				TradeType.SELL, BigDecimal.valueOf(810));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(7143371), Timestamp.valueOf("2016-05-12 10:11:23.250"),
				TradeType.SELL, BigDecimal.valueOf(1306));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(7219828), Timestamp.valueOf("2016-05-12 10:13:51.656"),
				TradeType.BUY, BigDecimal.valueOf(383));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(895148), Timestamp.valueOf("2016-05-12 10:12:41.79"),
				TradeType.SELL, BigDecimal.valueOf(1885));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(441269), Timestamp.valueOf("2016-05-12 10:19:27.428"),
				TradeType.SELL, BigDecimal.valueOf(552));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(9629185), Timestamp.valueOf("2016-05-12 10:20:30.80"),
				TradeType.SELL, BigDecimal.valueOf(822));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(461727), Timestamp.valueOf("2016-05-12 10:16:48.829"),
				TradeType.BUY, BigDecimal.valueOf(1296));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(1767170), Timestamp.valueOf("2016-05-12 10:15:18.809"),
				TradeType.SELL, BigDecimal.valueOf(1253));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(5094014), Timestamp.valueOf("2016-05-12 10:18:39.108"),
				TradeType.SELL, BigDecimal.valueOf(1802));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(327535), Timestamp.valueOf("2016-05-12 10:11:30.65"),
				TradeType.SELL, BigDecimal.valueOf(1767));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(744152), Timestamp.valueOf("2016-05-12 10:17:32.28"),
				TradeType.BUY, BigDecimal.valueOf(1280));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(8867293), Timestamp.valueOf("2016-05-12 10:16:52.595"),
				TradeType.BUY, BigDecimal.valueOf(529));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(4682118), Timestamp.valueOf("2016-05-12 10:13:2.947"),
				TradeType.SELL, BigDecimal.valueOf(1002));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(6379867), Timestamp.valueOf("2016-05-12 10:19:22.193"),
				TradeType.BUY, BigDecimal.valueOf(1938));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(2507192), Timestamp.valueOf("2016-05-12 10:12:55.499"),
				TradeType.SELL, BigDecimal.valueOf(1951));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(6038258), Timestamp.valueOf("2016-05-12 10:13:39.378"),
				TradeType.SELL, BigDecimal.valueOf(1891));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(2406943), Timestamp.valueOf("2016-05-12 10:10:2.507"),
				TradeType.SELL, BigDecimal.valueOf(1294));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(2903430), Timestamp.valueOf("2016-05-12 10:17:23.175"),
				TradeType.SELL, BigDecimal.valueOf(1039));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(7020694), Timestamp.valueOf("2016-05-12 10:10:40.985"),
				TradeType.SELL, BigDecimal.valueOf(72));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(3779535), Timestamp.valueOf("2016-05-12 10:10:27.596"),
				TradeType.SELL, BigDecimal.valueOf(1036));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(4621879), Timestamp.valueOf("2016-05-12 10:12:55.842"),
				TradeType.SELL, BigDecimal.valueOf(1288));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(6181950), Timestamp.valueOf("2016-05-12 10:18:6.180"),
				TradeType.BUY, BigDecimal.valueOf(1038));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(1097718), Timestamp.valueOf("2016-05-12 10:15:45.278"),
				TradeType.SELL, BigDecimal.valueOf(798));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(1084410), Timestamp.valueOf("2016-05-12 10:13:53.862"),
				TradeType.SELL, BigDecimal.valueOf(1626));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(3675453), Timestamp.valueOf("2016-05-12 10:14:34.861"),
				TradeType.SELL, BigDecimal.valueOf(1008));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(2020532), Timestamp.valueOf("2016-05-12 10:19:24.230"),
				TradeType.BUY, BigDecimal.valueOf(0));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(1993534), Timestamp.valueOf("2016-05-12 10:11:6.284"),
				TradeType.BUY, BigDecimal.valueOf(1010));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(711291), Timestamp.valueOf("2016-05-12 10:13:36.988"),
				TradeType.SELL, BigDecimal.valueOf(1429));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(5959893), Timestamp.valueOf("2016-05-12 10:10:44.479"),
				TradeType.SELL, BigDecimal.valueOf(143));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(6235217), Timestamp.valueOf("2016-05-12 10:10:49.406"),
				TradeType.SELL, BigDecimal.valueOf(1228));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(9503683), Timestamp.valueOf("2016-05-12 10:11:38.892"),
				TradeType.BUY, BigDecimal.valueOf(174));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(4598776), Timestamp.valueOf("2016-05-12 10:20:11.501"),
				TradeType.SELL, BigDecimal.valueOf(76));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(3857546), Timestamp.valueOf("2016-05-12 10:16:58.56"),
				TradeType.BUY, BigDecimal.valueOf(471));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(9509647), Timestamp.valueOf("2016-05-12 10:19:57.628"),
				TradeType.SELL, BigDecimal.valueOf(1076));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(2193555), Timestamp.valueOf("2016-05-12 10:20:51.972"),
				TradeType.BUY, BigDecimal.valueOf(1323));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(1278778), Timestamp.valueOf("2016-05-12 10:13:13.615"),
				TradeType.BUY, BigDecimal.valueOf(1288));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(3207832), Timestamp.valueOf("2016-05-12 10:17:30.478"),
				TradeType.BUY, BigDecimal.valueOf(1311));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(7351319), Timestamp.valueOf("2016-05-12 10:11:56.560"),
				TradeType.BUY, BigDecimal.valueOf(1691));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(8921900), Timestamp.valueOf("2016-05-12 10:11:15.384"),
				TradeType.SELL, BigDecimal.valueOf(1345));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(6267557), Timestamp.valueOf("2016-05-12 10:12:39.178"),
				TradeType.SELL, BigDecimal.valueOf(473));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(7117122), Timestamp.valueOf("2016-05-12 10:14:35.410"),
				TradeType.SELL, BigDecimal.valueOf(807));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(6490833), Timestamp.valueOf("2016-05-12 10:17:49.525"),
				TradeType.SELL, BigDecimal.valueOf(249));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(5903563), Timestamp.valueOf("2016-05-12 10:18:59.348"),
				TradeType.BUY, BigDecimal.valueOf(108));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(6729518), Timestamp.valueOf("2016-05-12 10:17:55.152"),
				TradeType.BUY, BigDecimal.valueOf(913));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(7492842), Timestamp.valueOf("2016-05-12 10:15:30.126"),
				TradeType.SELL, BigDecimal.valueOf(1621));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(6230845), Timestamp.valueOf("2016-05-12 10:11:15.492"),
				TradeType.SELL, BigDecimal.valueOf(743));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(9163592), Timestamp.valueOf("2016-05-12 10:18:42.65"),
				TradeType.SELL, BigDecimal.valueOf(140));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(6933692), Timestamp.valueOf("2016-05-12 10:12:55.678"),
				TradeType.BUY, BigDecimal.valueOf(1594));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(2883751), Timestamp.valueOf("2016-05-12 10:19:15.627"),
				TradeType.SELL, BigDecimal.valueOf(136));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(714563), Timestamp.valueOf("2016-05-12 10:19:0.897"),
				TradeType.SELL, BigDecimal.valueOf(1531));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(8593994), Timestamp.valueOf("2016-05-12 10:18:18.69"),
				TradeType.SELL, BigDecimal.valueOf(1391));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(3407938), Timestamp.valueOf("2016-05-12 10:16:39.384"),
				TradeType.BUY, BigDecimal.valueOf(1641));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(8619595), Timestamp.valueOf("2016-05-12 10:20:41.233"),
				TradeType.BUY, BigDecimal.valueOf(1683));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(4786313), Timestamp.valueOf("2016-05-12 10:12:54.401"),
				TradeType.SELL, BigDecimal.valueOf(47));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(6411300), Timestamp.valueOf("2016-05-12 10:17:54.609"),
				TradeType.BUY, BigDecimal.valueOf(1355));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(1356447), Timestamp.valueOf("2016-05-12 10:17:46.307"),
				TradeType.BUY, BigDecimal.valueOf(1656));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(3581922), Timestamp.valueOf("2016-05-12 10:16:4.883"),
				TradeType.SELL, BigDecimal.valueOf(789));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(2601751), Timestamp.valueOf("2016-05-12 10:13:35.209"),
				TradeType.SELL, BigDecimal.valueOf(207));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(9331741), Timestamp.valueOf("2016-05-12 10:14:0.395"),
				TradeType.SELL, BigDecimal.valueOf(1289));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(6740101), Timestamp.valueOf("2016-05-12 10:20:40.69"),
				TradeType.SELL, BigDecimal.valueOf(592));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(6545200), Timestamp.valueOf("2016-05-12 10:17:36.813"),
				TradeType.SELL, BigDecimal.valueOf(1683));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(7889259), Timestamp.valueOf("2016-05-12 10:15:51.803"),
				TradeType.SELL, BigDecimal.valueOf(1044));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(3600458), Timestamp.valueOf("2016-05-12 10:10:39.798"),
				TradeType.BUY, BigDecimal.valueOf(1185));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(6900281), Timestamp.valueOf("2016-05-12 10:10:29.130"),
				TradeType.BUY, BigDecimal.valueOf(374));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(7466457), Timestamp.valueOf("2016-05-12 10:12:20.870"),
				TradeType.BUY, BigDecimal.valueOf(1918));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(1009356), Timestamp.valueOf("2016-05-12 10:19:55.551"),
				TradeType.BUY, BigDecimal.valueOf(227));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(6115446), Timestamp.valueOf("2016-05-12 10:20:15.597"),
				TradeType.BUY, BigDecimal.valueOf(699));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(487197), Timestamp.valueOf("2016-05-12 10:16:57.245"),
				TradeType.BUY, BigDecimal.valueOf(1428));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(4922249), Timestamp.valueOf("2016-05-12 10:16:0.607"),
				TradeType.BUY, BigDecimal.valueOf(754));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(7874250), Timestamp.valueOf("2016-05-12 10:16:18.246"),
				TradeType.BUY, BigDecimal.valueOf(856));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(4769687), Timestamp.valueOf("2016-05-12 10:19:2.908"),
				TradeType.SELL, BigDecimal.valueOf(1329));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(5837386), Timestamp.valueOf("2016-05-12 10:13:8.773"),
				TradeType.SELL, BigDecimal.valueOf(1016));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(5229303), Timestamp.valueOf("2016-05-12 10:16:56.237"),
				TradeType.SELL, BigDecimal.valueOf(755));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(3509099), Timestamp.valueOf("2016-05-12 10:14:4.983"),
				TradeType.SELL, BigDecimal.valueOf(158));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(3928957), Timestamp.valueOf("2016-05-12 10:10:20.357"),
				TradeType.SELL, BigDecimal.valueOf(248));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(5590394), Timestamp.valueOf("2016-05-12 10:17:14.856"),
				TradeType.SELL, BigDecimal.valueOf(1823));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(4097126), Timestamp.valueOf("2016-05-12 10:20:59.510"),
				TradeType.SELL, BigDecimal.valueOf(291));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(2375580), Timestamp.valueOf("2016-05-12 10:17:2.157"),
				TradeType.BUY, BigDecimal.valueOf(1305));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(4665913), Timestamp.valueOf("2016-05-12 10:17:25.898"),
				TradeType.SELL, BigDecimal.valueOf(1225));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(6164652), Timestamp.valueOf("2016-05-12 10:20:20.551"),
				TradeType.SELL, BigDecimal.valueOf(336));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(9646356), Timestamp.valueOf("2016-05-12 10:14:29.21"),
				TradeType.SELL, BigDecimal.valueOf(514));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(7656676), Timestamp.valueOf("2016-05-12 10:14:15.33"),
				TradeType.BUY, BigDecimal.valueOf(1301));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(3794936), Timestamp.valueOf("2016-05-12 10:16:51.757"),
				TradeType.SELL, BigDecimal.valueOf(765));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(8523778), Timestamp.valueOf("2016-05-12 10:12:49.372"),
				TradeType.BUY, BigDecimal.valueOf(370));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(43259), Timestamp.valueOf("2016-05-12 10:14:13.551"),
				TradeType.SELL, BigDecimal.valueOf(237));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(7371953), Timestamp.valueOf("2016-05-12 10:19:8.986"),
				TradeType.SELL, BigDecimal.valueOf(890));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(4904716), Timestamp.valueOf("2016-05-12 10:16:13.229"),
				TradeType.SELL, BigDecimal.valueOf(745));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(9588444), Timestamp.valueOf("2016-05-12 10:14:30.966"),
				TradeType.BUY, BigDecimal.valueOf(977));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(8226066), Timestamp.valueOf("2016-05-12 10:17:23.449"),
				TradeType.BUY, BigDecimal.valueOf(1116));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(8341867), Timestamp.valueOf("2016-05-12 10:14:30.353"),
				TradeType.SELL, BigDecimal.valueOf(1557));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(2035940), Timestamp.valueOf("2016-05-12 10:20:0.939"),
				TradeType.BUY, BigDecimal.valueOf(608));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(7534787), Timestamp.valueOf("2016-05-12 10:18:35.603"),
				TradeType.BUY, BigDecimal.valueOf(1809));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(1887795), Timestamp.valueOf("2016-05-12 10:13:10.373"),
				TradeType.SELL, BigDecimal.valueOf(1215));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(92571), Timestamp.valueOf("2016-05-12 10:11:51.498"),
				TradeType.BUY, BigDecimal.valueOf(1650));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(8438009), Timestamp.valueOf("2016-05-12 10:17:29.493"),
				TradeType.SELL, BigDecimal.valueOf(1475));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(3358662), Timestamp.valueOf("2016-05-12 10:15:26.480"),
				TradeType.SELL, BigDecimal.valueOf(1605));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(225290), Timestamp.valueOf("2016-05-12 10:12:45.959"),
				TradeType.BUY, BigDecimal.valueOf(297));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(2269539), Timestamp.valueOf("2016-05-12 10:20:25.788"),
				TradeType.BUY, BigDecimal.valueOf(1740));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(1257550), Timestamp.valueOf("2016-05-12 10:16:32.465"),
				TradeType.SELL, BigDecimal.valueOf(1220));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(3603076), Timestamp.valueOf("2016-05-12 10:14:35.916"),
				TradeType.BUY, BigDecimal.valueOf(1113));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(5208174), Timestamp.valueOf("2016-05-12 10:18:54.116"),
				TradeType.SELL, BigDecimal.valueOf(1880));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(9110798), Timestamp.valueOf("2016-05-12 10:15:11.635"),
				TradeType.SELL, BigDecimal.valueOf(1742));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(3264200), Timestamp.valueOf("2016-05-12 10:12:48.765"),
				TradeType.SELL, BigDecimal.valueOf(1245));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(5905717), Timestamp.valueOf("2016-05-12 10:14:36.577"),
				TradeType.SELL, BigDecimal.valueOf(1827));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(6848807), Timestamp.valueOf("2016-05-12 10:17:50.164"),
				TradeType.BUY, BigDecimal.valueOf(150));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(9537097), Timestamp.valueOf("2016-05-12 10:11:30.215"),
				TradeType.BUY, BigDecimal.valueOf(474));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(2866043), Timestamp.valueOf("2016-05-12 10:17:11.215"),
				TradeType.BUY, BigDecimal.valueOf(1414));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(5618826), Timestamp.valueOf("2016-05-12 10:11:1.48"),
				TradeType.BUY, BigDecimal.valueOf(1699));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(4348363), Timestamp.valueOf("2016-05-12 10:15:50.589"),
				TradeType.BUY, BigDecimal.valueOf(127));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(4271911), Timestamp.valueOf("2016-05-12 10:14:27.77"),
				TradeType.BUY, BigDecimal.valueOf(361));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(2261593), Timestamp.valueOf("2016-05-12 10:12:28.944"),
				TradeType.BUY, BigDecimal.valueOf(14));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(5060683), Timestamp.valueOf("2016-05-12 10:10:39.518"),
				TradeType.SELL, BigDecimal.valueOf(513));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(7412279), Timestamp.valueOf("2016-05-12 10:20:6.687"),
				TradeType.BUY, BigDecimal.valueOf(693));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(1304876), Timestamp.valueOf("2016-05-12 10:16:4.689"),
				TradeType.BUY, BigDecimal.valueOf(881));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(2206513), Timestamp.valueOf("2016-05-12 10:14:18.748"),
				TradeType.SELL, BigDecimal.valueOf(1430));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(6895608), Timestamp.valueOf("2016-05-12 10:10:40.830"),
				TradeType.BUY, BigDecimal.valueOf(1468));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(9282799), Timestamp.valueOf("2016-05-12 10:10:1.276"),
				TradeType.SELL, BigDecimal.valueOf(365));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(4633062), Timestamp.valueOf("2016-05-12 10:20:52.867"),
				TradeType.SELL, BigDecimal.valueOf(331));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(6117391), Timestamp.valueOf("2016-05-12 10:19:28.218"),
				TradeType.SELL, BigDecimal.valueOf(920));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(4385288), Timestamp.valueOf("2016-05-12 10:17:26.558"),
				TradeType.BUY, BigDecimal.valueOf(1502));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(5117422), Timestamp.valueOf("2016-05-12 10:16:45.145"),
				TradeType.BUY, BigDecimal.valueOf(177));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(6677379), Timestamp.valueOf("2016-05-12 10:18:40.990"),
				TradeType.SELL, BigDecimal.valueOf(1444));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(3292478), Timestamp.valueOf("2016-05-12 10:10:2.886"),
				TradeType.BUY, BigDecimal.valueOf(1647));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(8597323), Timestamp.valueOf("2016-05-12 10:20:19.834"),
				TradeType.SELL, BigDecimal.valueOf(550));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(4086365), Timestamp.valueOf("2016-05-12 10:13:9.232"),
				TradeType.SELL, BigDecimal.valueOf(448));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(5486770), Timestamp.valueOf("2016-05-12 10:16:25.412"),
				TradeType.BUY, BigDecimal.valueOf(1065));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(3970511), Timestamp.valueOf("2016-05-12 10:11:57.978"),
				TradeType.BUY, BigDecimal.valueOf(60));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(2587049), Timestamp.valueOf("2016-05-12 10:18:33.675"),
				TradeType.SELL, BigDecimal.valueOf(917));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(1249112), Timestamp.valueOf("2016-05-12 10:18:34.765"),
				TradeType.BUY, BigDecimal.valueOf(1590));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(1347633), Timestamp.valueOf("2016-05-12 10:10:38.954"),
				TradeType.BUY, BigDecimal.valueOf(1636));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(8430353), Timestamp.valueOf("2016-05-12 10:15:29.142"),
				TradeType.BUY, BigDecimal.valueOf(961));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(6545340), Timestamp.valueOf("2016-05-12 10:20:27.161"),
				TradeType.BUY, BigDecimal.valueOf(1556));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(2839557), Timestamp.valueOf("2016-05-12 10:20:26.884"),
				TradeType.BUY, BigDecimal.valueOf(1842));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(3181305), Timestamp.valueOf("2016-05-12 10:20:18.707"),
				TradeType.BUY, BigDecimal.valueOf(1585));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(2771876), Timestamp.valueOf("2016-05-12 10:15:57.532"),
				TradeType.SELL, BigDecimal.valueOf(1047));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(4662350), Timestamp.valueOf("2016-05-12 10:17:17.783"),
				TradeType.BUY, BigDecimal.valueOf(5));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(1429581), Timestamp.valueOf("2016-05-12 10:20:51.879"),
				TradeType.SELL, BigDecimal.valueOf(466));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(3973989), Timestamp.valueOf("2016-05-12 10:10:11.656"),
				TradeType.BUY, BigDecimal.valueOf(483));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(1528404), Timestamp.valueOf("2016-05-12 10:11:32.856"),
				TradeType.SELL, BigDecimal.valueOf(1142));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(8324967), Timestamp.valueOf("2016-05-12 10:12:23.343"),
				TradeType.SELL, BigDecimal.valueOf(738));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(6648011), Timestamp.valueOf("2016-05-12 10:16:56.276"),
				TradeType.SELL, BigDecimal.valueOf(747));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(428833), Timestamp.valueOf("2016-05-12 10:19:55.376"),
				TradeType.BUY, BigDecimal.valueOf(1449));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(5033601), Timestamp.valueOf("2016-05-12 10:16:49.443"),
				TradeType.SELL, BigDecimal.valueOf(696));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(8991230), Timestamp.valueOf("2016-05-12 10:20:44.377"),
				TradeType.BUY, BigDecimal.valueOf(1574));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(4892535), Timestamp.valueOf("2016-05-12 10:19:18.274"),
				TradeType.SELL, BigDecimal.valueOf(600));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(3890288), Timestamp.valueOf("2016-05-12 10:15:49.311"),
				TradeType.BUY, BigDecimal.valueOf(1271));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(9271804), Timestamp.valueOf("2016-05-12 10:18:23.458"),
				TradeType.SELL, BigDecimal.valueOf(1985));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(6565392), Timestamp.valueOf("2016-05-12 10:18:42.317"),
				TradeType.SELL, BigDecimal.valueOf(1560));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(8906929), Timestamp.valueOf("2016-05-12 10:15:43.200"),
				TradeType.SELL, BigDecimal.valueOf(1166));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(6432520), Timestamp.valueOf("2016-05-12 10:13:43.930"),
				TradeType.BUY, BigDecimal.valueOf(1825));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(9567109), Timestamp.valueOf("2016-05-12 10:10:15.355"),
				TradeType.BUY, BigDecimal.valueOf(457));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(9164239), Timestamp.valueOf("2016-05-12 10:18:21.848"),
				TradeType.SELL, BigDecimal.valueOf(667));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(8381522), Timestamp.valueOf("2016-05-12 10:17:10.654"),
				TradeType.BUY, BigDecimal.valueOf(781));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(3267038), Timestamp.valueOf("2016-05-12 10:10:35.448"),
				TradeType.SELL, BigDecimal.valueOf(149));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(2900029), Timestamp.valueOf("2016-05-12 10:13:12.386"),
				TradeType.BUY, BigDecimal.valueOf(1386));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(3170153), Timestamp.valueOf("2016-05-12 10:13:28.528"),
				TradeType.SELL, BigDecimal.valueOf(686));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(4214571), Timestamp.valueOf("2016-05-12 10:18:4.143"),
				TradeType.SELL, BigDecimal.valueOf(411));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(5865105), Timestamp.valueOf("2016-05-12 10:13:4.29"),
				TradeType.SELL, BigDecimal.valueOf(404));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(8590479), Timestamp.valueOf("2016-05-12 10:12:13.550"),
				TradeType.BUY, BigDecimal.valueOf(268));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(1405440), Timestamp.valueOf("2016-05-12 10:14:47.241"),
				TradeType.SELL, BigDecimal.valueOf(644));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(3111524), Timestamp.valueOf("2016-05-12 10:13:3.386"),
				TradeType.BUY, BigDecimal.valueOf(1871));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(3809724), Timestamp.valueOf("2016-05-12 10:14:15.211"),
				TradeType.SELL, BigDecimal.valueOf(1297));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(7039350), Timestamp.valueOf("2016-05-12 10:11:34.910"),
				TradeType.BUY, BigDecimal.valueOf(1275));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(8925985), Timestamp.valueOf("2016-05-12 10:20:49.134"),
				TradeType.SELL, BigDecimal.valueOf(325));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(9799037), Timestamp.valueOf("2016-05-12 10:12:1.710"),
				TradeType.BUY, BigDecimal.valueOf(428));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(9468940), Timestamp.valueOf("2016-05-12 10:16:8.808"),
				TradeType.SELL, BigDecimal.valueOf(253));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(3245213), Timestamp.valueOf("2016-05-12 10:14:14.480"),
				TradeType.BUY, BigDecimal.valueOf(1606));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(4909009), Timestamp.valueOf("2016-05-12 10:14:45.587"),
				TradeType.BUY, BigDecimal.valueOf(1019));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(9168551), Timestamp.valueOf("2016-05-12 10:12:54.579"),
				TradeType.BUY, BigDecimal.valueOf(278));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(2333425), Timestamp.valueOf("2016-05-12 10:16:0.197"),
				TradeType.BUY, BigDecimal.valueOf(216));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(711399), Timestamp.valueOf("2016-05-12 10:18:13.560"),
				TradeType.SELL, BigDecimal.valueOf(1593));
		tradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(711401), Timestamp.valueOf("2016-05-12 10:20:15.562"),
				TradeType.SELL, BigDecimal.valueOf(1856));
		tradeList.add(tradeDetails);
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());

	}

	/*
	 * Basic functionality test, Dividend Yield
	 */
	@Test
	public void testDividendYieldOrPERatio_Dividend() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		BigDecimal price = null;
		String stockSymbol = null;
		List<Stock> localStockList = null;
		assertEquals(ErrorStrings.stockListError, StockUtility.dividendYieldOrPERatio(price, stockSymbol, localStockList,
				StockFunctionType.DIVIDEND_YIELD));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Basic functionality test, PE Ratio
	 */
	@Test
	public void testDividendYieldOrPERatio_PERatio() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		BigDecimal price = null;
		String stockSymbol = null;
		List<Stock> localStockList = null;
		assertEquals(ErrorStrings.stockListError,
				StockUtility.dividendYieldOrPERatio(price, stockSymbol, localStockList, StockFunctionType.PE_RATIO));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Basic functionality test, Record Trade
	 */
	@Test
	public void testRecordTrade_Basic() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		String stockSymbol = null;
		BigDecimal quantity = null;
		TradeType typeOfTrade = null;
		BigDecimal price = null;
		List<Trade> localTradeList = null;
		Timestamp currentTime = null;
		assertEquals(ErrorStrings.stockSymbolError,
				StockUtility.recordTrade(stockSymbol, quantity, currentTime, typeOfTrade, price, localTradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Basic functionality test, Volume Weighted Average
	 */
	@Test
	public void testVolumeWeightedStockPrice_Basic() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		String stockSymbol = null;
		Timestamp currentTime = null;
		assertEquals(ErrorStrings.stockSymbolError, StockUtility.volumeWeightedStockPrice(stockSymbol, currentTime,
				tradeList, VolumeWeightType.FIVE_MINUTES));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());

	}

	/*
	 * Basic functionality test, Geometric Mean
	 */
	@Test
	public void testGeometricMean_Basic() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> localTradeList = null;
		List<Stock> localStockList = null;
		assertEquals(ErrorStrings.stockListError, StockUtility.geometricMean(localStockList, localTradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	/*
	 * Dividend Yield - EMplty List
	 */
	@Test
	public void testDividendYieldOrPERatio_Dividend_ListEmpty() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		BigDecimal price = new BigDecimal(0);
		String stockSymbol = new String("ABC");
		List<Stock> localStockList = new ArrayList<Stock>();
		assertEquals(ErrorStrings.stockListError, StockUtility.dividendYieldOrPERatio(price, stockSymbol, localStockList,
				StockFunctionType.DIVIDEND_YIELD));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for Stock TEA
	 */
	@Test
	public void testDividendYield_TEA() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals("0E-20", StockUtility
				.dividendYieldOrPERatio(BigDecimal.valueOf(31), "TEA", stockList, StockFunctionType.DIVIDEND_YIELD)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for Stock GIN
	 */
	@Test
	public void testDividendYield_GIN() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals("0.05405405405405405406", StockUtility
				.dividendYieldOrPERatio(BigDecimal.valueOf(37), "GIN", stockList, StockFunctionType.DIVIDEND_YIELD)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for Stock COFF
	 */
	@Test
	public void testDividendYield_COFF() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals("0.04545454545454545455", StockUtility
				.dividendYieldOrPERatio(BigDecimal.valueOf(22), "COFF", stockList, StockFunctionType.DIVIDEND_YIELD)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for Stock WAT
	 */
	@Test
	public void testDividendYield_WAT() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals("0.42000000000000000000", StockUtility
				.dividendYieldOrPERatio(BigDecimal.valueOf(25), "WAT", stockList, StockFunctionType.DIVIDEND_YIELD)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for Stock PEP
	 */
	@Test
	public void testDividendYield_PEP() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals("0.63636363636363636364", StockUtility
				.dividendYieldOrPERatio(BigDecimal.valueOf(33), "PEP", stockList, StockFunctionType.DIVIDEND_YIELD)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield/PE Ratio for Stock with Wrong Symbol
	 */
	@Test
	public void testDividendYieldOrPERatio_WrongSymbol() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.stockSymbolNotFound, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(31),
				"TIN", stockList, StockFunctionType.DIVIDEND_YIELD));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield/PE Ratio for Stock with 0 price
	 */
	@Test
	public void testDividendYieldOrPERatio_ZeroPrice() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.stockPriceError, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(0), "TEA",
				stockList, StockFunctionType.DIVIDEND_YIELD));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield/PE Ratio for Stock with -ve price
	 */
	@Test
	public void testDividendYieldOrPERatio_NegativePrice() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.stockPriceError, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(-1), "TEA",
				stockList, StockFunctionType.DIVIDEND_YIELD));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield/PE Ratio for Stock with null stockSymbol
	 */
	@Test
	public void testDividendYieldOrPERatio_NullSymbol() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.stockSymbolError, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(31), null,
				stockList, StockFunctionType.DIVIDEND_YIELD));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield/PE Ratio for Stock with null stockPrice
	 */
	@Test
	public void testDividendYieldOrPERatio_NullStockPrice() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.stockPriceError,
				StockUtility.dividendYieldOrPERatio(null, "TEA", stockList, StockFunctionType.DIVIDEND_YIELD));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for null Last Dividend
	 */
	@Test
	public void testDividendYield_NullLastDividend() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = null;
		stockDetails = new Stock("COFF", StockType.COMMON, null, BigDecimal.valueOf(13), BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.valueOf(37), BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(35),
				BigDecimal.valueOf(30));
		localStockList.add(stockDetails);

		assertEquals(ErrorStrings.lastDividendError, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(22), "COFF",
				localStockList, StockFunctionType.DIVIDEND_YIELD).toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for Neg Last Dividend
	 */
	@Test
	public void testDividendYield_NegLastDividend() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = null;
		stockDetails = new Stock("COFF", StockType.COMMON, BigDecimal.valueOf(-1), BigDecimal.valueOf(13),
				BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.valueOf(37), BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(35),
				BigDecimal.valueOf(30));
		localStockList.add(stockDetails);

		assertEquals(ErrorStrings.lastDividendError, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(22), "COFF",
				localStockList, StockFunctionType.DIVIDEND_YIELD).toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for null fixed Dividend
	 */
	@Test
	public void testDividendYield_NullFixedDividend() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = null;
		stockDetails = new Stock("COFF", StockType.COMMON, null, BigDecimal.valueOf(13), BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.valueOf(37), BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), null, BigDecimal.valueOf(30));
		localStockList.add(stockDetails);

		assertEquals(ErrorStrings.fixedDividendError, StockUtility
				.dividendYieldOrPERatio(BigDecimal.valueOf(25), "WAT", localStockList, StockFunctionType.DIVIDEND_YIELD)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for Neg fixed Dividend
	 */
	@Test
	public void testDividendYield_ZeroFixedDividend() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = null;
		stockDetails = new Stock("COFF", StockType.COMMON, null, BigDecimal.valueOf(13), BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.valueOf(37), BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(-1),
				BigDecimal.valueOf(30));
		localStockList.add(stockDetails);

		assertEquals(ErrorStrings.fixedDividendError, StockUtility
				.dividendYieldOrPERatio(BigDecimal.valueOf(25), "WAT", localStockList, StockFunctionType.DIVIDEND_YIELD)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for null par value
	 */
	@Test
	public void testDividendYield_NullParValue() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = null;
		stockDetails = new Stock("COFF", StockType.COMMON, null, BigDecimal.valueOf(13), BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.valueOf(37), BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(35), null);
		localStockList.add(stockDetails);

		assertEquals(ErrorStrings.parValueError, StockUtility
				.dividendYieldOrPERatio(BigDecimal.valueOf(25), "WAT", localStockList, StockFunctionType.DIVIDEND_YIELD)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test Dividend Yield for Neg par value
	 */
	@Test
	public void testDividendYield_ZeroParValue() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = null;
		stockDetails = new Stock("COFF", StockType.COMMON, null, BigDecimal.valueOf(13), BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.valueOf(37), BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(35),
				BigDecimal.valueOf(-1));
		localStockList.add(stockDetails);

		assertEquals(ErrorStrings.parValueError, StockUtility
				.dividendYieldOrPERatio(BigDecimal.valueOf(25), "WAT", localStockList, StockFunctionType.DIVIDEND_YIELD)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test PE Ratio for Stock with null stockFunction
	 */
	@Test
	public void testPERatio_NullStockFunction() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.stockFunctionFlagError,
				StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(31), "TEA", stockList, null));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test PE Ratio for Null Last Dividend In List
	 */
	@Test
	public void testPERatio_NullDividendInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = null;
		stockDetails = new Stock("COFF", StockType.COMMON, BigDecimal.valueOf(1), BigDecimal.valueOf(13),
				BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, null, BigDecimal.valueOf(6), BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(35),
				BigDecimal.valueOf(30));
		localStockList.add(stockDetails);

		assertEquals(ErrorStrings.lastDividendError, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(58), "JAE",
				localStockList, StockFunctionType.PE_RATIO));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test PE Ratio for Null Last Dividend In List
	 */
	@Test
	public void testPERatio_ZeroDividendInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = null;
		stockDetails = new Stock("COFF", StockType.COMMON, BigDecimal.valueOf(1), BigDecimal.valueOf(13),
				BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.ZERO, BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(35),
				BigDecimal.valueOf(30));
		localStockList.add(stockDetails);

		assertEquals(ErrorStrings.lastDividendError, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(58), "JAE",
				localStockList, StockFunctionType.PE_RATIO));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test PE Ratio for -ve Last Dividend In List
	 */
	@Test
	public void testPERatio_NegDividendInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = null;
		stockDetails = new Stock("COFF", StockType.COMMON, BigDecimal.valueOf(1), BigDecimal.valueOf(13),
				BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.valueOf(-1), BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(35),
				BigDecimal.valueOf(30));
		localStockList.add(stockDetails);

		assertEquals(ErrorStrings.lastDividendError, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(58), "JAE",
				localStockList, StockFunctionType.PE_RATIO));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test PE Ratio for Stock COFF
	 */
	@Test
	public void testPERatio_COFF() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals("34.00000000000000000000",
				StockUtility
						.dividendYieldOrPERatio(BigDecimal.valueOf(34), "COFF", stockList, StockFunctionType.PE_RATIO)
						.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test PE Ratio for Stock JAE
	 */
	@Test
	public void testPERatio_JAE() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals("1.56756756756756756757",
				StockUtility
						.dividendYieldOrPERatio(BigDecimal.valueOf(58), "JAE", stockList, StockFunctionType.PE_RATIO)
						.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Test PE Ratio for Stock TEA
	 */
	@Test
	public void testPERatio_TEA() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.lastDividendError, StockUtility.dividendYieldOrPERatio(BigDecimal.valueOf(94), "TEA",
				stockList, StockFunctionType.PE_RATIO));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());

	}

	/*
	 * Record Trade with null Stock Symbol
	 */
	@Test
	public void testRecordTrade_NullStockSymbol() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.stockSymbolError, StockUtility.recordTrade(null, BigDecimal.valueOf(4909009),
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, BigDecimal.valueOf(1019), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with null Trade Quantity
	 */
	@Test
	public void testRecordTrade_NullTradeQuantity() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradeQuantityError, StockUtility.recordTrade("WAT", null,
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, BigDecimal.valueOf(1019), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with neg Trade Quantity
	 */
	@Test
	public void testRecordTrade_NegTradeQuantity() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradeQuantityError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(-1),
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, BigDecimal.valueOf(1019), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with zero Trade Quantity
	 */
	@Test
	public void testRecordTrade_ZeroTradeQuantity() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradeQuantityError, StockUtility.recordTrade("WAT", BigDecimal.ZERO,
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, BigDecimal.valueOf(1019), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with null Time Stamp
	 */
	@Test
	public void testRecordTrade_NullTimeStamp() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.currentTimeError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(4909009), null,
				TradeType.BUY, BigDecimal.valueOf(1019), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with zero Time Stamp
	 */
	@Test
	public void testRecordTrade_ZeroTimeStamp() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.currentTimeError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(4909009),
				new Timestamp(0), TradeType.BUY, BigDecimal.valueOf(1019), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with null Trade Type
	 */
	@Test
	public void testRecordTrade_NullTradeType() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradeTypeFlagError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(4909009),
				Timestamp.valueOf("2016-05-12 10:14:45.587"), null, BigDecimal.valueOf(1019), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with null Trade Price
	 */
	@Test
	public void testRecordTrade_NullTradePrice() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradePriceError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(4909009),
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, null, tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with neg Trade Price
	 */
	@Test
	public void testRecordTrade_NegTradePrice() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradePriceError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(4909009),
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, BigDecimal.valueOf(-1), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with 0 Trade Price
	 */
	@Test
	public void testRecordTrade_ZeroTradePrice() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradePriceError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(4909009),
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, BigDecimal.ZERO, tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with null Trade List
	 */
	@Test
	public void testRecordTrade_NullTradeList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> localTradeList = null;
		assertEquals(ErrorStrings.tradeListError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(4909009),
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, BigDecimal.valueOf(1019), localTradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with Empty Trade List
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRecordTrade_EmptyTradeList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> localTradeList = new ArrayList<Trade>();
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		localTradeList = (List<Trade>) StockUtility.recordTrade("WAT", BigDecimal.valueOf(4909009), currentTime,
				TradeType.BUY, BigDecimal.valueOf(1019), localTradeList);

		Iterator<Trade> localIterator = localTradeList.listIterator();

		assertTrue(localIterator.hasNext());

		Trade testTrade = localIterator.next();

		assertEquals("WAT", testTrade.getStockSymbol());
		assertEquals(BigDecimal.valueOf(4909009), testTrade.getQuantity());
		assertEquals(currentTime, testTrade.getTradeTimeStamp());
		assertEquals(TradeType.BUY, testTrade.getTradeTypeObj());
		assertEquals(BigDecimal.valueOf(1019), testTrade.getTradePrice());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with null Trade Price
	 */
	@Test
	public void testRecordTrade_EmptyTradePrice() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradePriceError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(4909009),
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, BigDecimal.valueOf(0), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with null Trade Quantity
	 */
	@Test
	public void testRecordTrade_EmptyTradeQuantity() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradeQuantityError, StockUtility.recordTrade("WAT", BigDecimal.valueOf(0),
				Timestamp.valueOf("2016-05-12 10:14:45.587"), TradeType.BUY, BigDecimal.valueOf(1019), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with Standard Trade List. Confirm. Remove Trade. Confirm
	 * Deletion.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRecordTrade_StandardTradeList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		tradeList = (List<Trade>) StockUtility.recordTrade("WAT", BigDecimal.valueOf(15634832),
				Timestamp.valueOf("2016-05-12 10:12:45.587"), TradeType.SELL, BigDecimal.valueOf(1253), tradeList);

		Trade testTrade = tradeList.get(tradeList.size() - 1);

		assertEquals("WAT", testTrade.getStockSymbol());
		assertEquals(BigDecimal.valueOf(15634832), testTrade.getQuantity());
		assertEquals(Timestamp.valueOf("2016-05-12 10:12:45.587"), testTrade.getTradeTimeStamp());
		assertEquals(TradeType.SELL, testTrade.getTradeTypeObj());
		assertEquals(BigDecimal.valueOf(1253), testTrade.getTradePrice());

		tradeList.remove(tradeList.size() - 1);
		testTrade = tradeList.get(tradeList.size() - 1);

		assertEquals("COK", testTrade.getStockSymbol());
		assertEquals(BigDecimal.valueOf(711401), testTrade.getQuantity());
		assertEquals(Timestamp.valueOf("2016-05-12 10:20:15.562"), testTrade.getTradeTimeStamp());
		assertEquals(TradeType.SELL, testTrade.getTradeTypeObj());
		assertEquals(BigDecimal.valueOf(1856), testTrade.getTradePrice());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Record Trade with Duplicate Trade in List
	 */
	@Test
	public void testRecordTrade_Duplicate() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.duplicateFoundError, StockUtility.recordTrade("ALE", BigDecimal.valueOf(7981112),
				Timestamp.valueOf("2016-05-12 10:18:23.575"), TradeType.SELL, BigDecimal.valueOf(94), tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - null time
	 */
	@Test
	public void testVolumeWeightedStockPrice_NullTime() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		String stockSymbol = "AAA";
		Timestamp currentTime = null;
		assertEquals(ErrorStrings.currentTimeError, StockUtility.volumeWeightedStockPrice(stockSymbol, currentTime,
				tradeList, VolumeWeightType.FIVE_MINUTES));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - null stock symbol
	 */
	@Test
	public void testVolumeWeightedStockPrice_NullStockSymbol() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		String stockSymbol = null;
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		assertEquals(ErrorStrings.stockSymbolError, StockUtility.volumeWeightedStockPrice(stockSymbol, currentTime,
				tradeList, VolumeWeightType.FIVE_MINUTES));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - null stock symbol
	 */
	@Test
	public void testVolumeWeightedStockPrice_NullVolumeWeightType() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		String stockSymbol = "AAA";
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		assertEquals(ErrorStrings.volumeWeightFlagError,
				StockUtility.volumeWeightedStockPrice(stockSymbol, currentTime, tradeList, null));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - null trade list
	 */
	@Test
	public void testVolumeWeightedStockPrice_NullTradeList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> localTradeList = null;
		String stockSymbol = "AAA";
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		assertEquals(ErrorStrings.tradeListError, StockUtility.volumeWeightedStockPrice(stockSymbol, currentTime,
				localTradeList, VolumeWeightType.FIVE_MINUTES));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - empty trade list
	 */
	@Test
	public void testVolumeWeightedStockPrice_ZeroTradeList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> localTradeList = new ArrayList<Trade>();
		String stockSymbol = "AAA";
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		assertEquals(ErrorStrings.tradeListError, StockUtility.volumeWeightedStockPrice(stockSymbol, currentTime,
				localTradeList, VolumeWeightType.FIVE_MINUTES));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - ALE
	 */
	@Test
	public void testVolumeWeightedStockPrice_ALE() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		String stockSymbol = "ALE";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals("919.73255553122342814958",
				StockUtility
						.volumeWeightedStockPrice(stockSymbol, currentTime, tradeList, VolumeWeightType.FIVE_MINUTES)
						.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - JOE
	 */
	@Test
	public void testVolumeWeightedStockPrice_JOE() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		String stockSymbol = "JOE";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals("1085.92154356063432375174",
				StockUtility
						.volumeWeightedStockPrice(stockSymbol, currentTime, tradeList, VolumeWeightType.FIVE_MINUTES)
						.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - COK
	 */
	@Test
	public void testVolumeWeightedStockPrice_COK() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		String stockSymbol = "COK";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals("1107.14745551529110738609",
				StockUtility
						.volumeWeightedStockPrice(stockSymbol, currentTime, tradeList, VolumeWeightType.FIVE_MINUTES)
						.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - null stock symbol in list
	 */
	@Test
	public void testVolumeWeightedStockPrice_NullSymbolInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> faultyTradeList = new ArrayList<Trade>();
		Trade tradeDetails = new Trade();

		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(4922249), Timestamp.valueOf("2016-05-12 10:16:0.607"),
				TradeType.BUY, BigDecimal.valueOf(754));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade(null, BigDecimal.valueOf(7874250), Timestamp.valueOf("2016-05-12 10:16:18.246"),
				TradeType.BUY, BigDecimal.valueOf(856));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(4769687), Timestamp.valueOf("2016-05-12 10:19:2.908"),
				TradeType.SELL, BigDecimal.valueOf(1329));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(5837386), Timestamp.valueOf("2016-05-12 10:13:8.773"),
				TradeType.SELL, BigDecimal.valueOf(1016));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(5229303), Timestamp.valueOf("2016-05-12 10:16:56.237"),
				TradeType.SELL, BigDecimal.valueOf(755));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(3509099), Timestamp.valueOf("2016-05-12 10:14:4.983"),
				TradeType.SELL, BigDecimal.valueOf(158));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(3928957), Timestamp.valueOf("2016-05-12 10:10:20.357"),
				TradeType.SELL, BigDecimal.valueOf(248));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(5590394), Timestamp.valueOf("2016-05-12 10:17:14.856"),
				TradeType.SELL, BigDecimal.valueOf(1823));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(4097126), Timestamp.valueOf("2016-05-12 10:20:59.510"),
				TradeType.SELL, BigDecimal.valueOf(291));
		faultyTradeList.add(tradeDetails);

		String stockSymbol = "COFF";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals(ErrorStrings.stockSymbolError, StockUtility
				.volumeWeightedStockPrice(stockSymbol, currentTime, faultyTradeList, VolumeWeightType.FIVE_MINUTES)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - null timestamp in list
	 */
	@Test
	public void testVolumeWeightedStockPrice_NullTimestampInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> faultyTradeList = new ArrayList<Trade>();
		Trade tradeDetails = new Trade();

		tradeDetails = new Trade("JAE", BigDecimal.valueOf(1993534), Timestamp.valueOf("2016-05-12 10:11:6.284"),
				TradeType.BUY, BigDecimal.valueOf(1010));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(711291), null, TradeType.SELL, BigDecimal.valueOf(1429));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(5959893), Timestamp.valueOf("2016-05-12 10:10:44.479"),
				TradeType.SELL, BigDecimal.valueOf(143));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(6235217), Timestamp.valueOf("2016-05-12 10:10:49.406"),
				TradeType.SELL, BigDecimal.valueOf(1228));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(9503683), Timestamp.valueOf("2016-05-12 10:11:38.892"),
				TradeType.BUY, BigDecimal.valueOf(174));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(4598776), Timestamp.valueOf("2016-05-12 10:20:11.501"),
				TradeType.SELL, BigDecimal.valueOf(76));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(3857546), Timestamp.valueOf("2016-05-12 10:16:58.56"),
				TradeType.BUY, BigDecimal.valueOf(471));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(9509647), Timestamp.valueOf("2016-05-12 10:19:57.628"),
				TradeType.SELL, BigDecimal.valueOf(1076));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(2193555), Timestamp.valueOf("2016-05-12 10:20:51.972"),
				TradeType.BUY, BigDecimal.valueOf(1323));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(1278778), Timestamp.valueOf("2016-05-12 10:13:13.615"),
				TradeType.BUY, BigDecimal.valueOf(1288));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(3207832), Timestamp.valueOf("2016-05-12 10:17:30.478"),
				TradeType.BUY, BigDecimal.valueOf(1311));
		faultyTradeList.add(tradeDetails);

		String stockSymbol = "COK";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals(ErrorStrings.tradeTimeError, StockUtility
				.volumeWeightedStockPrice(stockSymbol, currentTime, faultyTradeList, VolumeWeightType.FIVE_MINUTES)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - -ve quantity in list
	 */
	@Test
	public void testVolumeWeightedStockPrice_NegQuantityInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> faultyTradeList = new ArrayList<Trade>();
		Trade tradeDetails = new Trade();

		tradeDetails = new Trade("WAT", BigDecimal.valueOf(6848807), Timestamp.valueOf("2016-05-12 10:17:50.164"),
				TradeType.BUY, BigDecimal.valueOf(150));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(9537097), Timestamp.valueOf("2016-05-12 10:11:30.215"),
				TradeType.BUY, BigDecimal.valueOf(474));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(2866043), Timestamp.valueOf("2016-05-12 10:17:11.215"),
				TradeType.BUY, BigDecimal.valueOf(1414));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(5618826), Timestamp.valueOf("2016-05-12 10:11:1.48"),
				TradeType.BUY, BigDecimal.valueOf(1699));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(4348363), Timestamp.valueOf("2016-05-12 10:15:50.589"),
				TradeType.BUY, BigDecimal.valueOf(127));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(-1), Timestamp.valueOf("2016-05-12 10:14:27.77"),
				TradeType.BUY, BigDecimal.valueOf(361));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(2261593), Timestamp.valueOf("2016-05-12 10:12:28.944"),
				TradeType.BUY, BigDecimal.valueOf(14));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(5060683), Timestamp.valueOf("2016-05-12 10:10:39.518"),
				TradeType.SELL, BigDecimal.valueOf(513));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(7412279), Timestamp.valueOf("2016-05-12 10:20:6.687"),
				TradeType.BUY, BigDecimal.valueOf(693));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(1304876), Timestamp.valueOf("2016-05-12 10:16:4.689"),
				TradeType.BUY, BigDecimal.valueOf(881));
		faultyTradeList.add(tradeDetails);

		String stockSymbol = "GIN";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals(ErrorStrings.tradeQuantityError, StockUtility
				.volumeWeightedStockPrice(stockSymbol, currentTime, faultyTradeList, VolumeWeightType.FIVE_MINUTES)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - 0 quantity in list
	 */
	@Test
	public void testVolumeWeightedStockPrice_ZeroQuantityInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> faultyTradeList = new ArrayList<Trade>();
		Trade tradeDetails = new Trade();

		tradeDetails = new Trade("GIN", BigDecimal.valueOf(6379867), Timestamp.valueOf("2016-05-12 10:19:22.193"),
				TradeType.BUY, BigDecimal.valueOf(1938));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(2507192), Timestamp.valueOf("2016-05-12 10:12:55.499"),
				TradeType.SELL, BigDecimal.valueOf(1951));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(6038258), Timestamp.valueOf("2016-05-12 10:13:39.378"),
				TradeType.SELL, BigDecimal.valueOf(1891));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(2406943), Timestamp.valueOf("2016-05-12 10:10:2.507"),
				TradeType.SELL, BigDecimal.valueOf(1294));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(2903430), Timestamp.valueOf("2016-05-12 10:17:23.175"),
				TradeType.SELL, BigDecimal.valueOf(1039));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(7020694), Timestamp.valueOf("2016-05-12 10:10:40.985"),
				TradeType.SELL, BigDecimal.valueOf(72));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(3779535), Timestamp.valueOf("2016-05-12 10:10:27.596"),
				TradeType.SELL, BigDecimal.valueOf(1036));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(4621879), Timestamp.valueOf("2016-05-12 10:12:55.842"),
				TradeType.SELL, BigDecimal.valueOf(1288));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(6181950), Timestamp.valueOf("2016-05-12 10:18:6.180"),
				TradeType.BUY, BigDecimal.valueOf(1038));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(1097718), Timestamp.valueOf("2016-05-12 10:15:45.278"),
				TradeType.SELL, BigDecimal.valueOf(798));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(1084410), Timestamp.valueOf("2016-05-12 10:13:53.862"),
				TradeType.SELL, BigDecimal.valueOf(1626));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.ZERO, Timestamp.valueOf("2016-05-12 10:14:34.861"), TradeType.SELL,
				BigDecimal.valueOf(1008));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(2020532), Timestamp.valueOf("2016-05-12 10:19:24.230"),
				TradeType.BUY, BigDecimal.valueOf(0));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(1993534), Timestamp.valueOf("2016-05-12 10:11:6.284"),
				TradeType.BUY, BigDecimal.valueOf(1010));

		String stockSymbol = "JAE";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals(ErrorStrings.tradeQuantityError, StockUtility
				.volumeWeightedStockPrice(stockSymbol, currentTime, faultyTradeList, VolumeWeightType.FIVE_MINUTES)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - null quantity in list
	 */
	@Test
	public void testVolumeWeightedStockPrice_NullQuantityInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> faultyTradeList = new ArrayList<Trade>();
		Trade tradeDetails = new Trade();

		tradeDetails = new Trade("GIN", BigDecimal.valueOf(8226066), Timestamp.valueOf("2016-05-12 10:17:23.449"),
				TradeType.BUY, BigDecimal.valueOf(1116));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(8341867), Timestamp.valueOf("2016-05-12 10:14:30.353"),
				TradeType.SELL, BigDecimal.valueOf(1557));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(2035940), Timestamp.valueOf("2016-05-12 10:20:0.939"),
				TradeType.BUY, BigDecimal.valueOf(608));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COFF", BigDecimal.valueOf(7534787), Timestamp.valueOf("2016-05-12 10:18:35.603"),
				TradeType.BUY, BigDecimal.valueOf(1809));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", null, Timestamp.valueOf("2016-05-12 10:13:10.373"), TradeType.SELL,
				BigDecimal.valueOf(1215));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(92571), Timestamp.valueOf("2016-05-12 10:11:51.498"),
				TradeType.BUY, BigDecimal.valueOf(1650));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(8438009), Timestamp.valueOf("2016-05-12 10:17:29.493"),
				TradeType.SELL, BigDecimal.valueOf(1475));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(3358662), Timestamp.valueOf("2016-05-12 10:15:26.480"),
				TradeType.SELL, BigDecimal.valueOf(1605));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(225290), Timestamp.valueOf("2016-05-12 10:12:45.959"),
				TradeType.BUY, BigDecimal.valueOf(297));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(2269539), Timestamp.valueOf("2016-05-12 10:20:25.788"),
				TradeType.BUY, BigDecimal.valueOf(1740));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(1257550), Timestamp.valueOf("2016-05-12 10:16:32.465"),
				TradeType.SELL, BigDecimal.valueOf(1220));
		faultyTradeList.add(tradeDetails);

		String stockSymbol = "JOE";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals(ErrorStrings.tradeQuantityError, StockUtility
				.volumeWeightedStockPrice(stockSymbol, currentTime, faultyTradeList, VolumeWeightType.FIVE_MINUTES)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - -ve price in list
	 */
	@Test
	public void testVolumeWeightedStockPrice_NegPriceInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> faultyTradeList = new ArrayList<Trade>();
		Trade tradeDetails = new Trade();

		tradeDetails = new Trade("WAT", BigDecimal.valueOf(8590479), Timestamp.valueOf("2016-05-12 10:12:13.550"),
				TradeType.BUY, BigDecimal.valueOf(268));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(1405440), Timestamp.valueOf("2016-05-12 10:14:47.241"),
				TradeType.SELL, BigDecimal.valueOf(644));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(3111524), Timestamp.valueOf("2016-05-12 10:13:3.386"),
				TradeType.BUY, BigDecimal.valueOf(-1));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(3809724), Timestamp.valueOf("2016-05-12 10:14:15.211"),
				TradeType.SELL, BigDecimal.valueOf(1297));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(7039350), Timestamp.valueOf("2016-05-12 10:11:34.910"),
				TradeType.BUY, BigDecimal.valueOf(1275));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(8925985), Timestamp.valueOf("2016-05-12 10:20:49.134"),
				TradeType.SELL, BigDecimal.valueOf(325));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(9799037), Timestamp.valueOf("2016-05-12 10:12:1.710"),
				TradeType.BUY, BigDecimal.valueOf(428));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(9468940), Timestamp.valueOf("2016-05-12 10:16:8.808"),
				TradeType.SELL, BigDecimal.valueOf(253));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(3245213), Timestamp.valueOf("2016-05-12 10:14:14.480"),
				TradeType.BUY, BigDecimal.valueOf(1606));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(4909009), Timestamp.valueOf("2016-05-12 10:14:45.587"),
				TradeType.BUY, BigDecimal.valueOf(1019));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(9168551), Timestamp.valueOf("2016-05-12 10:12:54.579"),
				TradeType.BUY, BigDecimal.valueOf(278));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JAE", BigDecimal.valueOf(2333425), Timestamp.valueOf("2016-05-12 10:16:0.197"),
				TradeType.BUY, BigDecimal.valueOf(216));
		faultyTradeList.add(tradeDetails);

		String stockSymbol = "PEP";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals(ErrorStrings.tradePriceError, StockUtility
				.volumeWeightedStockPrice(stockSymbol, currentTime, faultyTradeList, VolumeWeightType.FIVE_MINUTES)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - zero price in list
	 */
	@Test
	public void testVolumeWeightedStockPrice_ZeroPriceInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> faultyTradeList = new ArrayList<Trade>();
		Trade tradeDetails = new Trade();

		tradeDetails = new Trade("JOE", BigDecimal.valueOf(9164239), Timestamp.valueOf("2016-05-12 10:18:21.848"),
				TradeType.SELL, BigDecimal.valueOf(667));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(8381522), Timestamp.valueOf("2016-05-12 10:17:10.654"),
				TradeType.BUY, BigDecimal.valueOf(781));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(3267038), Timestamp.valueOf("2016-05-12 10:10:35.448"),
				TradeType.SELL, BigDecimal.valueOf(149));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(2900029), Timestamp.valueOf("2016-05-12 10:13:12.386"),
				TradeType.BUY, BigDecimal.valueOf(1386));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(3170153), Timestamp.valueOf("2016-05-12 10:13:28.528"),
				TradeType.SELL, BigDecimal.valueOf(686));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(4214571), Timestamp.valueOf("2016-05-12 10:18:4.143"),
				TradeType.SELL, BigDecimal.valueOf(411));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(5865105), Timestamp.valueOf("2016-05-12 10:13:4.29"),
				TradeType.SELL, BigDecimal.ZERO);
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(8590479), Timestamp.valueOf("2016-05-12 10:12:13.550"),
				TradeType.BUY, BigDecimal.valueOf(268));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(1405440), Timestamp.valueOf("2016-05-12 10:14:47.241"),
				TradeType.SELL, BigDecimal.valueOf(644));
		faultyTradeList.add(tradeDetails);

		String stockSymbol = "POP";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals(ErrorStrings.tradePriceError, StockUtility
				.volumeWeightedStockPrice(stockSymbol, currentTime, faultyTradeList, VolumeWeightType.FIVE_MINUTES)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Volume Weighted Average - null price in list
	 */
	@Test
	public void testVolumeWeightedStockPrice_NullPriceInList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Trade> faultyTradeList = new ArrayList<Trade>();
		Trade tradeDetails = new Trade();

		tradeDetails = new Trade("GIN", BigDecimal.valueOf(6164652), Timestamp.valueOf("2016-05-12 10:20:20.551"),
				TradeType.SELL, BigDecimal.valueOf(336));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("WAT", BigDecimal.valueOf(9646356), Timestamp.valueOf("2016-05-12 10:14:29.21"),
				TradeType.SELL, BigDecimal.valueOf(514));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(7656676), Timestamp.valueOf("2016-05-12 10:14:15.33"),
				TradeType.BUY, BigDecimal.valueOf(1301));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("POP", BigDecimal.valueOf(3794936), Timestamp.valueOf("2016-05-12 10:16:51.757"),
				TradeType.SELL, BigDecimal.valueOf(765));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("ALE", BigDecimal.valueOf(8523778), Timestamp.valueOf("2016-05-12 10:12:49.372"),
				TradeType.BUY, BigDecimal.valueOf(370));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("TEA", BigDecimal.valueOf(43259), Timestamp.valueOf("2016-05-12 10:14:13.551"),
				TradeType.SELL, null);
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("COK", BigDecimal.valueOf(7371953), Timestamp.valueOf("2016-05-12 10:19:8.986"),
				TradeType.SELL, BigDecimal.valueOf(890));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("JOE", BigDecimal.valueOf(4904716), Timestamp.valueOf("2016-05-12 10:16:13.229"),
				TradeType.SELL, BigDecimal.valueOf(745));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("PEP", BigDecimal.valueOf(9588444), Timestamp.valueOf("2016-05-12 10:14:30.966"),
				TradeType.BUY, BigDecimal.valueOf(977));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(8226066), Timestamp.valueOf("2016-05-12 10:17:23.449"),
				TradeType.BUY, BigDecimal.valueOf(1116));
		faultyTradeList.add(tradeDetails);
		tradeDetails = new Trade("GIN", BigDecimal.valueOf(8341867), Timestamp.valueOf("2016-05-12 10:14:30.353"),
				TradeType.SELL, BigDecimal.valueOf(1557));
		faultyTradeList.add(tradeDetails);

		String stockSymbol = "TEA";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals(ErrorStrings.tradePriceError, StockUtility
				.volumeWeightedStockPrice(stockSymbol, currentTime, faultyTradeList, VolumeWeightType.FIVE_MINUTES)
				.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());

	}

	/*
	 * Volume Weighted Average - unknown symbol
	 */
	@Test
	public void testVolumeWeightedStockPrice_UnknownSymbol() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		String stockSymbol = "LON";
		Timestamp currentTime = Timestamp.valueOf("2016-05-12 10:17:21.595");
		assertEquals(ErrorStrings.noRelevantTradesFound,
				StockUtility
						.volumeWeightedStockPrice(stockSymbol, currentTime, tradeList, VolumeWeightType.FIVE_MINUTES)
						.toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());

	}

	/*
	 * Geometric Mean
	 */
	@Test
	public void testGeometricMean() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals("941.78861012497632730776", StockUtility.geometricMean(stockList, tradeList).toString());
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Geometric Mean - null stock list
	 */
	@Test
	public void testGeometricMean_NullStockList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.stockListError, StockUtility.geometricMean(null, tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/*
	 * Geometric Mean - null trade list
	 */
	@Test
	public void testGeometricMean_NullTradeList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		assertEquals(ErrorStrings.tradeListError, StockUtility.geometricMean(stockList, null));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	/*
	 * Geometric Mean - null stock symbol in stock list
	 */
	@Test
	public void testGeometricMean_NullStockSymbolInStockList() throws Exception {
		logger.debug("BEGIN: "+new Object(){}.getClass().getEnclosingMethod().getName());
		List<Stock> localStockList = new ArrayList<Stock>();

		Stock stockDetails = new Stock();

		stockDetails = new Stock("TEA", StockType.COMMON, BigDecimal.valueOf(0), BigDecimal.valueOf(0),
				BigDecimal.valueOf(100));
		localStockList.add(stockDetails);
		stockDetails = new Stock("POP", StockType.COMMON, BigDecimal.valueOf(8), BigDecimal.valueOf(0),
				BigDecimal.valueOf(100));
		localStockList.add(stockDetails);
		stockDetails = new Stock("ALE", StockType.COMMON, BigDecimal.valueOf(23), BigDecimal.valueOf(0),
				BigDecimal.valueOf(60));
		localStockList.add(stockDetails);
		stockDetails = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(2),
				BigDecimal.valueOf(100));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JOE", StockType.COMMON, BigDecimal.valueOf(13), BigDecimal.valueOf(0),
				BigDecimal.valueOf(250));
		localStockList.add(stockDetails);
		stockDetails = new Stock(null, StockType.COMMON, BigDecimal.valueOf(1), BigDecimal.valueOf(13),
				BigDecimal.valueOf(140));
		localStockList.add(stockDetails);
		stockDetails = new Stock("JAE", StockType.PREFERRED, BigDecimal.valueOf(37), BigDecimal.valueOf(6),
				BigDecimal.valueOf(240));
		localStockList.add(stockDetails);
		stockDetails = new Stock("WAT", StockType.PREFERRED, BigDecimal.valueOf(60), BigDecimal.valueOf(35),
				BigDecimal.valueOf(30));
		localStockList.add(stockDetails);
		stockDetails = new Stock("PEP", StockType.COMMON, BigDecimal.valueOf(21), BigDecimal.valueOf(7),
				BigDecimal.valueOf(80));
		localStockList.add(stockDetails);
		stockDetails = new Stock("COK", StockType.COMMON, BigDecimal.valueOf(24), BigDecimal.valueOf(8),
				BigDecimal.valueOf(470));
		localStockList.add(stockDetails);
		
		assertEquals(ErrorStrings.stockSymbolError, StockUtility.geometricMean(localStockList, tradeList));
		logger.debug("SUCCESS: "+new Object(){}.getClass().getEnclosingMethod().getName());
	}
}
