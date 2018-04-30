<span class="c39 c37 c32"></span>

<span class="c37 c32 c39"></span>

<span class="c39 c37 c32"></span>

<span class="c39 c37 c32"></span>

<span class="c39 c37 c32"></span>

<span class="c39 c37 c32"></span>

<span class="c39 c37 c32"></span>

<span class="c39 c37 c32"></span>

<span class="c39 c37 c32"></span>

<span class="c27 c10">JBookTrader</span>

<span class="c8">User Guide</span>

<span class="c8"></span>

<span class="c8"></span>

<span class="c8"></span>

<span class="c1"> </span>

<span class="c1"> </span>

<span class="c1"> </span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"> </span>

<span class="c1"> </span>

<span class="c0">Last Updated: Tuesday, November 1, 2011</span>

<span class="c0">Eugene Kononov, Martin Koistinen, Allen Whitt&Shaggs</span>

<span class="c0"></span>

* * *

<span class="c40 c37 c25 c10 c50">Disclaimer</span>

<span class="c1">This SOFTWARE PRODUCT is provided “as is” and “with all faults and defects.” The AUTHORS express no warranties of any kind, and claim no responsibility for events resulting directly or indirectly from the use of the SOFTWARE PRODUCT. As with any software, there are inherent risks resulting from the use of the SOFTWARE PRODUCT, 3rd party libraries, operating systems, and the hardware they run on. Trading of financial securities also bears significant risk including factors related to the exchanges, brokerages, and electronic communications between all parties. These risks are magnified by the use of margin and financial derivative products. It is the responsibility of the END USER to review the provided source code and third party software to determine if the system is safe, and to adequately test the system. It is the END USER who must evaluate all risks of the SOFTWARE PRODUCT, and it is the END USER who must bear all risks resulting from the use of the SOFTWARE PRODUCT. It is assumed the END USER is an experienced computer programmer, and can evaluate the provided source code. The AUTHORS are not responsible for any loss of data, wealth, time, or anything else of value from the use, modification, or distribution of the SOFTWARE PRODUCT.</span>

<span class="c1"></span>

<span class="c1">This USERS MANUAL is provided only to familiarize the user with the capabilities of the software, and is provided “as is” and “with all faults, inaccuracies, and defects.” This USERS MANUAL does not mention all risks involved, and is not meant as a guide to influence decisions relating to the purchase or sale of financial securities. It is expected that the END USER is an experienced trader of financial securities, and the END USER will use the SOFTWARE PRODUCT as a tool to facilitate the END USER’s personal trading decisions.</span>

* * *

<span class="c25 c10 c37">Table of Contents</span><span class="c22 c40 c37"> </span>

<span class="c22 c40 c37"></span>

<span class="c22 c37 c40"></span>

1.  <span class="c7 c29 c14 c10">[Summary](#h.b82a0a152313)</span>
2.  <span class="c7 c29 c14 c10">[System Requirements](#h.bb7269a4cd0e)</span>
3.  <span class="c7 c29 c14 c10">[Installation](#h.7d0469bddc4a)</span>
4.  <span class="c7 c14 c10 c29">[Running JBookTrader](#h.e9373dd0e793)</span>
5.  <span class="c7 c29 c14 c10">[JBookTrader Operations](#h.5ca0e8cd8703)</span>
6.  <span class="c7 c29 c14 c10">[Market Data Display](#h.fa12f5f3a5a9)</span>
7.  <span class="c7 c29 c14 c10">[Strategy Running Modes](#h.a50b210d2d7f)</span>
8.  <span class="c7 c29 c14 c10">[Back Testing](#h.3c2d85303ed2)</span>
9.  <span class="c7 c29 c14 c10">[Forward Testing](#h.4929de0b2c6c)</span>
10.  <span class="c7 c29 c14 c10">[Using simulated TWS Account](#h.06f1e27ba9e0)</span>
11.  <span class="c7 c29 c14 c10">[Strategy Optimization](#h.71e323ae64bf)</span>

1.  <span class="c7 c22 c14">[Optimization Options](#h.df909c9ca6dc)</span>

1.  <span class="c7 c22 c14">[Historical Data](#h.97af3f6bcf4c)</span>
2.  <span class="c7 c22 c14">[Search Method](#h.eb49877327c0)</span>
3.  <span class="c7 c22 c14">[Selection Criteria](#h.6dd652d5b7c5)</span>
4.  <span class="c7 c22 c14">[Minimum Trades](#h.0b28add53eaa)</span>

1.  <span class="c7 c22 c14">[Optimization Maps](#h.803406a19b65)</span>

1.  <span class="c7 c22 c14">[Horizontal](#h.29f72512f54d)</span>
2.  <span class="c7 c22 c14">[Vertical](#h.c56b5e084647)</span>
3.  <span class="c7 c22 c14">[Case](#h.759d79ae7106)</span>
4.  <span class="c7 c22 c14">[Color Map](#h.7bdbb5de44d4)</span>

1.  <span class="c7 c29 c14 c10">[Strategy Performance Chart](#h.0375f4323dd3)</span>
2.  <span class="c7 c29 c14 c10">[Strategy Performance Evaluation](#h.86022322dc44)</span>

1.  <span class="c7 c22 c14">[Trades](#h.4044345b7d79)</span>
2.  <span class="c7 c22 c14">[Percent Profitable](#h.190faa03216e)</span>
3.  <span class="c7 c22 c14">[Average Trade](#h.fc9632ad7718)</span>
4.  <span class="c7 c22 c14">[Net Profit](#h.47778467a7a0)</span>
5.  <span class="c7 c22 c14">[Max DD](#h.719605c9f99a)</span>
6.  <span class="c7 c22 c14">[Profit Factor](#h.ebc4be1e89c2)</span>
7.  <span class="c7 c14 c22">[Kelly](#h.c31171f63f6c)</span>
8.  <span class="c7 c22 c14">[Performance Index](#h.0878454bb3bd)</span>

1.  <span class="c7 c29 c14 c10">[Recording Market Data](#h.f313887d57c6)</span>
2.  <span class="c7 c29 c14 c10">[Trading](#h.a075381d30d9)</span>
3.  <span class="c7 c29 c14 c10">[Reporting](#h.b7de96489066)</span>
4.  <span class="c7 c29 c14 c10">[Preferences](#h.fdef34383d9a)</span>

1.  <span class="c7 c22 c14">[TWS Connection](#h.70ded2d8f2da)</span>
2.  <span class="c7 c22 c14">[Web Access](#h.2010a80a07bf)</span>
3.  <span class="c7 c22 c14">[Look & Feel](#h.bebdd4eab0af)</span>

1.  <span class="c7 c29 c14 c10">[Adding your own trading strategy](#h.2ef9c361df37)</span>
2.  <span class="c7 c29 c14 c10">[Literature](#h.2f3cf59eacdd)</span>
3.  <span class="c7 c29 c14 c10">[Technical Support](#h.a1fbba1803f1)</span>

<a id="id.516b993f8f23"></a>

# <span> </span><a id="id.4bb965142f3e"></a>

* * *

<span class="c1">Summary</span>

<span class="c1">JBookTrader is a fully automated trading system (ATS) that can trade various types of market securities during the trading day without user monitoring. All aspects of trading, such as obtaining prices, analyzing price patterns, making trading decisions, placing orders, monitoring order executions, and controlling the risk are automated according to the user preferences. The central idea behind JBookTrader is to completely remove the emotions from trading, so that the trading system can systematically and consistently follow a predefined set of trading and risk management rules.</span>

<span class="c1">The features include strategy back testing, strategy optimization, intra-day data retrieval, and real time trading via the Interactive Brokers API.</span>

<span class="c1"> </span>

<span class="c1">JBookTrader is intended for software developers. It is not an "off-the-shelf" product that can be installed and run. Instead, JBookTrader provides a framework for developing automated trading systems and requires a certain amount of programming knowledge and experience. The users can modify any part of the source code, implement their own trading strategies, and customize the system in any way. If you are not a software developer or if you don't have much experience programming in Java, JBookTrader is probably not for you.</span>

<span class="c1"></span>

<span class="c1"></span>

# <span class="c27 c10">System Requirements</span>

<span class="c1">To use JBookTrader, you need the following:</span>

<span class="c1"> </span>

*   <span class="c1">A universal brokerage account with Interactive Brokers</span>
*   <span class="c1">Trader's Workstation (TWS) version 895.3 or higher</span>
*   <span class="c1">An Interactive Brokers market data subscription to the securities of interest (such as stocks or futures)</span>
*   <span class="c1">Virtually any operating system (such as Windows, Mac OS, or Linux)</span>
*   <span class="c1">Java Development Kit (JDK) version 1.6 or higher</span>
*   <span class="c1">Java Integrated Development Environment (such as Eclipse, IntelliJIdea, NetBeans, or JBuilder)</span>

<span class="c1"> </span>

<span class="c1"></span>

# <span class="c27 c10">Installation</span>

<span>JBookTrader is distributed in a single archive file (JBookTrader.7z). The latest version is available at</span> <span class="c54">[http://code.google.com/p/jbooktrader/downloads/list](https://www.google.com/url?q=http://code.google.com/p/jbooktrader/downloads/list&sa=D&ust=1525081216078000)</span><span class="c1">. Unzip the contents of the JBookTrader.7z file to any destination directory on you machine, such as C:\JBookTrader.</span>

<span class="c1"> </span>

<span class="c1">To setup JBookTrader in a Java IDE (such as Eclipse, A Java IDE), follow the steps in the document JBookTraderSetup.Mac.Eclipse.pdf or JBookTraderSetup.Mac.Windows.pdf located in the /docs directory of the distribution.</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Running JBookTrader</span>

1.  <span class="c1">Start TWS normally and log in to your account (use a simulated account until you get comfortable with JBookTrader).  Make sure that the “Enable ActiveX and Socket Clients” and the "Bypass Order Precautions for API Orders" options are checked:</span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 624.00px; height: 531.33px;">![](images/image18.png)</span>

<span class="c1"> </span>

<span class="c1"></span>

<span class="c1"></span>

1.  <span class="c1">Start JBookTrader:</span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image20.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">  </span>

1.  <span>Verify that JBookTrader can connect to TWS: right-click (control-click on Mac) on any strategy row and select</span> <span class="c10">Forward Test</span><span class="c1">:</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image19.png)</span>

<span class="c1"></span>

<span class="c1">If the connection is successful and the security specified by the strategy is currently trading, you’ll see market information:</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image22.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

* * *

# <span class="c27 c10">JBookTrader Operations</span>

<span class="c1"></span>

<span class="c1">Once started with the trading strategies specified, JBookTrader does not require any user actions and monitoring, or even user presence. During the trading day, JBookTrader will continuously monitor the price action for the specified securities, determine if the predefined trade entry/exit conditions have been met, place the trades, monitor their execution status, and record all events in a report file.</span>

<span class="c1"> </span>

<span class="c1">Specifically, JBookTrader will continuously execute the following sequence for each running trading strategy:</span>

<span class="c1"> </span>

*   <span class="c1">Detect when the market depth changes (any one of the 10 best bids or 10 best asks changes in either price or quantity).  For the liquid securities (such as ES and YM), this may happen several times every second.</span>
*   <span class="c1">Recalculate technical indicators based on the new market depth.</span>
*   <span class="c1">Determine if the trading strategy calls for a new position based on the new information. If new position differs from the current position, JBookTrader will place an order. For example, let us suppose that the the strategy is currently long 5 contracts. If the market moves higher and strategy determines that the profit target is reached and that the new position should be flat (i.e. 0 contracts), JBookTrader will place a MKT order to sell 5 contracts at the market.</span>
*   <span class="c1">The strategy will wait until the order is fully filled.</span>
*   <span class="c1">The transaction will be recorded in both event report and strategy report.</span>

<span class="c1"></span>

<span class="c1"> </span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Market Data Display</span>

<span class="c1"></span>

<span>The market depth change at any point of time is reflected in the strategies table. The</span> <span class="c10">Market Depth</span> <span>column shows cumulative bid and the ask size. The</span> <span class="c10">Price</span><span class="c1"> column in JBookTrader is the midpoint between the current highest bid price and the current lowest ask price. At all times, the market data in JBookTrader is identical to that in TWS, and both applications reflect the changes almost simultaneously. Here is the TWS market depth window superimposed on JBookTrader for comparison:</span>

<span class="c1"> </span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 648.00px; height: 727.98px;">![](images/image21.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Strategy Running Modes</span>

<span>JBookTrader can run any trading strategy in the following four running modes: back testing, forward testing, optimization, and trading. No changes to the strategy are required to run it in any of the running modes. Furthermore, the back testing, forward testing, and optimization modes are</span> <span class="c25">result-consistent</span><span>. That is, if you run a strategy in the forward test mode, and then back test and optimize the same strategy over the same time period, the performance results (net profit, number of trades, profit factor, etc.) will be identical. The trading mode is</span> <span class="c25">approximately result-consistent</span><span class="c1"> with the forward testing and the back testing modes. That is because in real trading, the bid/ask spreads and the quality of executions may vary beyond the boundaries assumed by the back testing, forward testing, and optimization modes.</span>

<span class="c1"></span>

<span class="c1">The running modes and their characteristics are summarized in the table below:</span>

<span class="c1"></span>

<a id="t.7b1b078301822e6cf561f623a156f4fe7ff675ef"></a><a id="t.0"></a>

<table class="c46">

<tbody>

<tr class="c35">

<td class="c41" colspan="1" rowspan="1">

<span class="c31 c10">Running Mode</span>

</td>

<td class="c41" colspan="1" rowspan="1">

<span class="c31 c10">Purpose</span>

</td>

<td class="c41" colspan="1" rowspan="1">

<span class="c31 c10">TWS Connection and live market data</span>

</td>

<td class="c41" colspan="1" rowspan="1">

<span class="c31 c10">Historical Data File</span>

</td>

<td class="c41" colspan="1" rowspan="1">

<span class="c31 c10">Order placement and execution</span>

</td>

</tr>

<tr class="c35">

<td class="c3" colspan="1" rowspan="1">

<span class="c10 c24">Back Testing</span>

</td>

<td class="c3" colspan="1" rowspan="1">

<span class="c24 c32">Evaluation of historical strategy performance</span>

</td>

<td class="c3" colspan="1" rowspan="1">

<span class="c24 c32">Not used</span>

</td>

<td class="c3" colspan="1" rowspan="1">

<span class="c24 c32">Required</span>

</td>

<td class="c3" colspan="1" rowspan="1">

<span class="c24 c32">Simulated</span>

</td>

</tr>

<tr class="c35">

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c10">Forward Testing</span>

</td>

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c32">Evaluation of real time strategy performance</span>

</td>

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c32">Required</span>

</td>

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c32">Not used</span>

</td>

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c32">Simulated</span>

</td>

</tr>

<tr class="c35">

<td class="c3" colspan="1" rowspan="1">

<span class="c24 c10">Optimization</span>

</td>

<td class="c3" colspan="1" rowspan="1">

<span class="c24 c32">Discovery and calibration of strategy parameters</span>

</td>

<td class="c3" colspan="1" rowspan="1">

<span class="c24 c32">Not used</span>

</td>

<td class="c3" colspan="1" rowspan="1">

<span class="c24 c32">Required</span>

</td>

<td class="c3" colspan="1" rowspan="1">

<span class="c24 c32">Simulated</span>

</td>

</tr>

<tr class="c35">

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c10">Trading</span>

</td>

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c32">Live strategy trading</span>

</td>

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c32">Required</span>

</td>

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c32">Not used</span>

</td>

<td class="c38" colspan="1" rowspan="1">

<span class="c24 c32">Real</span>

</td>

</tr>

</tbody>

</table>

<span class="c24 c32"></span>

<span class="c1">All four trading modes are described in detail in the sections below.</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Back Testing</span>

<span class="c14 c32">Back testing is evaluating your trading strategy performance using historical market depth data. Historical data sets can be downloaded from the</span> <span class="c54">[Project Downloads Page](https://www.google.com/url?q=http://code.google.com/p/jbooktrader/downloads/list&sa=D&ust=1525081216087000)</span><span class="c1">.</span>

<span class="c1"></span>

<span class="c14 c32">To run a back test, right click on the strategy and select</span> <span class="c10 c14">Back Test</span><span class="c14 c32">:</span><span class="c1"> </span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image24.png)</span>

<span class="c1"></span>

<span class="c1">JBookTrader will pop up a file dialog. This dialog allows you to:</span>

<span class="c1"></span>

*   <span class="c1">Select a historical data file</span>
*   <span class="c1">Optionally select a subset of the historical data file by date</span>
*   <span class="c1">Optionally modify the strategy parameters for the back test</span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 289.32px;">![](images/image23.png)</span>

<span class="c1"></span>

<span class="c1">Press the "Back Test" button and JBookTrader will run the test and display the results:</span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image26.png)</span>

<span class="c1"> </span>

<span>To see the extended back testing results, right click on the strategy and select</span> <span class="c10">Information</span><span class="c1">:</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image25.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">Extended back testing results will be displayed:</span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 530.00px; height: 502.00px;">![](images/image27.png)</span>

* * *

# <span class="c27 c10">Forward Testing</span>

<span class="c1">Forward testing is evaluating your trading strategy performance using live market data. In the forward testing mode, JBookTrader will connect to TWS and run the strategy, but no actual trade orders will be submitted. This makes it possible to run JBookTrader against real TWS account without making any trades. Although a paper trading account can also be used, the data feed from that type of account is unreliable.</span>

<span class="c1"> </span>

<span>To start a forward test, right click on the strategy and select</span> <span class="c10">Forward Test</span><span class="c1">:</span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image19.png)</span>

<span class="c1"></span>

<span class="c1">If the security specified by the strategy is trading, the strategy line will be updated with live market data: </span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image22.png)</span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Using simulated TWS Account</span>

<span class="c48 c40 c14 c10"></span>

<span class="c1">Unfortunately, the "market depth" (and similarly "market book") functionality in simulated IB account is flawed. You can frequently see crossed market (bid higher than ask), missing rows of data, and downright wrong information. The best way to see it is to start two instances of TWS, and log in to your real account in one TWS instance, and log in to your simulated account in the other TWS instance. Then bring up the "market depth" window in each one, for the same security, and compare the two windows side by side. As market depth changes, you'll see that very frequently, the data is not the same in the two windows.</span>

<span class="c1"> </span>

<span class="c1">Here is how it may look like (notice the “crossed market” and other inconsistencies in the market depth for the simulated TWS account):</span>

<span class="c1"> </span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 567.00px; height: 568.00px;">![](images/image28.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">Since market depth data is critical to JBookTrader, simulated account simply doesn't cut it.</span>

<span class="c1"> </span>

<span class="c1">The solution is to use the "Forward Test" in JBookTrader with the real TWS account. In the "Forward Test" mode, everything that happens is exactly the same as if you were actually trading, except for one thing: no orders will be transmitted to TWS. For the performance evaluation, the executions will be simulated in a manner consistent with the real executions: all buy orders will be simulated to fill at the current ask, and all sell orders will be simulated to fill at the current bid.</span>

<span class="c1"> </span>

<span class="c1">The "Forward Test" mode should also be used if your intent is to record market depth.</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Strategy Optimization</span>

<span class="c40 c14 c10 c48"></span>

<span class="c1">Strategy optimization is a search for a set of strategy parameters that results in the best strategy performance. JBookTrader uses multiple measures of performance. You can select a particular measure to be used in the search.</span>

<span class="c1"> </span>

<span>To start a strategy optimization, right click on the strategy and select</span> <span class="c10">Optimize</span><span class="c1">:</span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image29.png)</span>

<span class="c1"></span>

<span class="c1"> </span>

<span class="c1">This will display the Strategy Optimizer dialog.  This dialog offers these options:</span>

<span class="c1"></span>

*   <span class="c1">The historical data file to use</span>
*   <span class="c1">Optionally select a subset of the historical data by date</span>
*   <span class="c1">Modify the strategy parameters ranges to test</span>
*   <span class="c1">The optimizer method to use</span>
*   <span class="c1">The selection criteria</span>
*   <span class="c1">The minimum number of trades</span>

<span class="c1"></span>

<span class="c1">These are described below.</span>

<span class="c1"></span>

* * *

## <span class="c9">Optimization Options</span>

<span class="c1">The optimization window offers a number of configurations.  These are shown here: </span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 497.09px;">![](images/image30.png)</span>

### <span class="c10 c36">Historical Data</span>

<span class="c1"></span>

<span class="c1">Select the desired historical data file by clicking "Browse...".  To use only a subset of this data, check "Use date range" and enter the desired time period.  Note that in the example above, only data from 00:00:00 on January 1, 2011, until 23:59:59 on April 30, 2011, will be used in the optimization process.</span>

### <span class="c36 c10">Search Method</span>

<a id="id.c895f50a8500"></a>

<span class="c1">There are currently two choices for the search method; "Brute Force" and "Divide & Conquer".  The simplest is Brute Force.  This method will systematically back test every possible combination of parameters to locate the highest scoring configuration based on your selection criteria and minimum number of trades.  Depending on the number of combinations of parameters, the size of the back testing data and the computing power of your computer, this method may require minutes, hours, days or even weeks to complete.</span>

<span class="c1"></span>

<span class="c1">Divide & Conquer takes a speedier approach by dividing the whole set of parameters combinations into a relatively small number of groups.  The method then scores a sample of combinations from each group.  Divide and Conquer will then divide the highest scoring group into a new set of sub-groups and sample these, recursively, until it can no longer sub-divide the highest scoring group.  While this method is very quick, it isn't as thorough as the brute force method.</span>

### <span class="c36 c10">Selection Criteria</span>

<a id="id.71efab7d4a94"></a>

<span>There are currently four options for the selection criteria; Profit Factor, Net Profit, Kelly (Kelly Criterion) and PI (Performance Index).  Each of these choices is described in the 12. </span><span class="c7">Strategy performance evaluation</span><span class="c1"> section of this document.</span>

<span class="c1"></span>

<span class="c1">The selection here determines how the search method scores the best combination of parameters.</span>

### <span class="c36 c10">Minimum Trades</span>

<a id="id.64e2eb10be8e"></a>

<span class="c1">The statistical relevance of the selection criteria depends greatly on the number of trades that occur over the optimization search.  The Minimum Trades field allows you to omit optimization results that produced a number of trades lower than this setting.</span>

<span class="c1"></span>

<span>Once these options are set, click the</span> <span class="c10">Optimize</span><span class="c1"> button:</span>

<span class="c1"></span>

<span class="c14 c32">After completion, optimization results will be shown:</span><span class="c1"> </span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 497.09px;">![](images/image31.png)</span>

<span class="c1"></span>

<span class="c1"></span>

* * *

## <span class="c9">Optimization Maps</span>

<span class="c1">Once an optimization has completed, it may be helpful to visualize a heat map of the parameter combinations showing the relative performance of each combination.</span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 504.00px;">![](images/image32.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">Each point in the map shows the relative performance of the combination of parameter values at that point.  In the case of the map shown above, each point represents a value combination for the Entry and Period parameters for this optimization run.  The color value represents the value of the selection criteria selected for the optimization run.</span>

<span class="c1"></span>

<span class="c1">As shown in the legend along the right edge of the window, the red areas in the map have the highest profit factor and the areas in the dark blue have the lowest profit factor.</span>

<span class="c1"></span>

<span class="c1">Areas in white have no result because these combinations of parameter values produced a number of trades below the Minimum trade threshold set for the optimization run, or in the case of a Divide & Conquer optimization run, because the optimizer did not test them at all. </span>

<span class="c1"></span>

<span class="c1">The map shown above represents a Brute Force optimization run.  A Divide and Conquer run of the same strategy and other settings would appear like this:</span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 504.00px;">![](images/image33.png)</span>

<span class="c1"></span>

<span class="c1">Here it is easy to see how the Divide and Conquer search method samples areas of the combination space then focuses on areas with the best relative performance.  In this case, the optimizer very sparsely tested the map except in the areas where Entry is between 10 and 13 and Period is between about 0 and 75.</span>

<span class="c1"></span>

<span class="c1">If desired a user may "zoom-in" to a part of the optimization map by dragging a box with his mouse around the area of interest.</span>

<span class="c1"></span>

<span class="c1">There are a number of options for the optimization map; Horizontal, Vertical, Case and Color Map.</span>

<span class="c1"></span>

### <span class="c36 c10">Horizontal</span>

<a id="id.8f7bfd0aafd5"></a>

<span class="c1">The Horizontal drop-down menu allows the selection of which parameter to map to the horizontal axis of the map.  This is more important when there are more than two parameters as the map can only show two at a time.</span>

### <span class="c36 c10">Vertical</span>

<span class="c1">The Vertical drop-down menu allows the selection of which parameter to map to the horizontal axis of the map. This is more important when there are more than two parameters as the map can only show two at a time.</span>

### <span class="c36 c10"></span>

<span class="c1">Case</span>

<a id="id.aaad64e8d89d"></a>

<span class="c1"> The optimizer map can only show two parameters at a time.  So, when an optimized strategy uses 3 or more parameters, the resulting P&L that is mapped may vary depending on the hidden parameters.  Selecting Best will color the map with the best P&L for the shown parameters and selecting Worst will show the worst P&L for those parameters.</span>

<span class="c1"></span>

<span class="c1">For example, suppose that a strategy with 3 parameters is optimized.  Let us further suppose that the P&L for some of the parameter combinations is as follows:</span>

<span class="c32 c52">(10, 20, 1) : </span><span class="c22 c43">-$1,000.00</span>

<span class="c17">(10, 20, 2) : $12,000.00</span>

<span class="c32 c52">(10, 20, 3) : </span><span class="c22 c43">-$5,000.00</span>

<span class="c22 c43"></span>

<span class="c1">If the optimization map is only displaying the first 2 parameters in the map, the color of the map for these parameters will be determined by the Case setting.  If set to Best, the map will be set to the color corresponding to $12,000.00, if Case is set to Worst, then the map will be set to the color corresponding to -$5,000.00.</span>

### <span class="c36 c10">Color Map</span>

<a id="id.5b5ff1e672b9"></a>

<span class="c1">The color map drop-down menu contains the choices; Heat and Gray.  Heat uses color to denote performance and Gray uses shades of grey to denote performance.  This might be useful if the viewer has trouble distinguishing colors or if the the map will be printed on a monotone printer or other medium.</span>

<span class="c1"></span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Strategy Performance Chart</span>

<span>In discretionary (manual) trading, charts are used</span> <span class="c25">before</span><span>a trading decision is made. Since JBookTrader is a fully automated trading system, there is no one to actually look at the chart. The system makes all decisions. However, the charts are still very useful for strategy evaluation</span> <span class="c25">after</span><span class="c1"> the period of trading.</span>

<span class="c1"> </span>

<span>To display a strategy performance chart, run any strategy in one of the three modes:</span> <span class="c10">Back Test</span><span>,</span> <span class="c10">Forward Test</span><span>, or</span> <span class="c10">Trade</span><span>. Next, right-click (control-click on Mac) on the strategy and select</span> <span class="c10">Chart</span><span class="c1">:</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image34.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">The strategy performance chart will be displayed:</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 497.09px;">![](images/image35.png)</span>

<span class="c1"></span>

<span class="c29 c40 c14 c10"> </span>

<span class="c1">The performance chart contains the information about the market and the strategy, since it has started. The green circles mark the times and the prices when the strategy took long positions, the red circles mark the times and the prices when the strategy took short positions, and the yellow circles mark the times and the prices when the strategy took flat positions. The number in the circle indicates the number of contracts/shares held by the strategy.</span>

<span class="c1"></span>

<span class="c1">The performance chart is zoom-able. To zoom in, left-click on the chart area and drag to the right. To zoom out, left-click on the chart area and drag to the left:</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 497.09px;">![](images/image9.png)</span>

<span class="c1"></span>

<span>There are numerous chart display options which you can adjust. To get to the options, right-click (control-click on Mac) on the chart and choose</span> <span class="c10">Properties</span><span class="c1">:</span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 497.09px;">![](images/image10.png)</span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Strategy Performance Evaluation</span>

<span class="c1"></span>

## <span class="c9">Trades</span>

<span class="c1">This is the total number of trades that executed during the strategy’s run. The greater the number of trades (and the longer the historical data period is), the more statistically significant the performance results will be.</span>

## <span class="c9">Percent Profitable</span>

<span class="c1">Percent of profitable trades.</span>

## <span class="c9">Average Trade</span>

<span class="c1">This is the total net profit divided by the number of trades.</span>

## <span class="c9">Net Profit</span>

<span class="c1">This is the total profit or loss of the strategy’s run.</span>

## <span class="c9">Max DD</span>

<span class="c1">The Maximum Drawdown indicates the largest decline from the highest peak during the course of the strategy's run.</span>

## <span class="c9">Profit Factor</span>

<span class="c1">Profit Factor is a measure of a strategy’s performance and is measured as the ratio:</span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 179.00px; height: 58.00px;">![](images/image11.png)</span>

## <span class="c9"></span>

<span class="c1">Kelly</span>

<span>Kelly provides an indication of the</span> <span class="c25">maximum</span><span>amount of trading capital that should be applied to the given strategy.  The result is in the range 0 - 100 and indicates the</span> <span class="c25">maximum</span><span class="c1"> percentage that should be allocated.  The Kelly Criterion is calculated with the formula:</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 105.00px; height: 58.00px;">![](images/image12.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">Where:</span>

<span class="c10 c25">f</span><span class="c1">  is the maximum fraction of the total capital to invest</span>

<span class="c25 c10">b</span><span class="c1"> is the win-loss ratio</span>

<span class="c25 c10">p</span><span class="c1"> is the probability of a winning trade</span>

<span class="c10">q</span><span class="c1"> is the probability of a losing trade</span>

<span class="c1"></span>

<span>For more information see</span> <span class="c7">[http://en.wikipedia.org/wiki/Kelly_criterion](https://www.google.com/url?q=http://en.wikipedia.org/wiki/Kelly_criterion&sa=D&ust=1525081216098000)</span><span class="c1">.</span>

<span class="c1"></span>

<span>In the trading systems domain, Kelly can be used for position sizing. It can also be used to directly compare the "goodness" of multiple strategies side by side. Let's suppose that system</span> <span class="c25">A</span><span>has Kelly of 5% while system</span> <span class="c25">B</span> <span>has Kelly of 50%. Clearly, system</span> <span class="c25">B is</span> <span>superior to system</span><span class="c25">A,</span> <span class="c1">because its distribution of winning and losing trades allows a much greater proportion of capital to risk on every trade.</span>

## <span class="c9">Performance Index</span>

<span class="c1">The Performance Index is another measure of the Strategy’s performance.  It is calculated as 10 times the average profit per trade divided by the standard deviation of trades:</span>

![](images/image1.png)

<span class="c39 c37 c32"></span>

<span class="c1">The higher the performance index is, the more "quality" the strategy has. Performance Index is closely related to System Quality Number, introduced by Van K. Tharp in his "Definitive Guide to Position Sizing".</span>

<span class="c1"></span>

<span class="c1">References:</span>

<span class="c7 c22 c14">[http://www.ninjatrader-support2.com/vb/showthread.php?t=4320](https://www.google.com/url?q=http://www.ninjatrader-support2.com/vb/showthread.php?t%3D4320&sa=D&ust=1525081216099000)</span>

<span class="c7 c22 c14">[http://www.iitm.com/Definitive-Guide-to-Position-Sizing.htm](https://www.google.com/url?q=http://www.iitm.com/Definitive-Guide-to-Position-Sizing.htm&sa=D&ust=1525081216099000)</span>

<span class="c7 c22 c14">[http://groups.google.com/group/jbooktrader/browse_thread/thread/f35707d6d1e5163f](https://www.google.com/url?q=http://groups.google.com/group/jbooktrader/browse_thread/thread/f35707d6d1e5163f&sa=D&ust=1525081216100000)</span>

<span class="c7 c22 c14">[](https://www.google.com/url?q=http://groups.google.com/group/jbooktrader/browse_thread/thread/f35707d6d1e5163f&sa=D&ust=1525081216100000)</span>

<span class="c7 c22 c14">[](https://www.google.com/url?q=http://groups.google.com/group/jbooktrader/browse_thread/thread/f35707d6d1e5163f&sa=D&ust=1525081216100000)</span>

* * *

# <span class="c27 c10">Recording Market Data</span>

<span class="c1"></span>

<span>When a strategy runs in either</span> <span class="c10">Forward Test</span><span>or</span> <span class="c10">Trade</span><span>mode, it accumulates market depth data and saves it in a file in the</span> <span class="c25">/marketData</span><span class="c1"> directory.</span>

<span class="c1"> </span>

<span class="c1">Open one of the saved files in a text editor. The market depth history is saved as a sequence of lines.  Each line represents a 1-second snapshot of the market and contains 4 columns:</span>

<span class="c1"> </span>

<span class="c1">Column  1: date in the MMddyy format</span>

<span class="c1"> Column  2: time in the HHmmss format</span>

<span class="c1"> Column  3: book balance</span>

<span class="c1"> Column  4: price</span>

<span class="c1"></span>

<span class="c1"> </span>

<span class="c1">Here is how it may look:</span>

<span class="c17">…</span>

<span class="c17"> 062409,081232,-11,894.375</span>

<span class="c17"> 062409,081233,-11,894.375</span>

<span class="c17"> 062409,081234,-11,894.375</span>

<span class="c17"> 062409,081235,-11,894.375</span>

<span class="c17"> 062409,081236,-11,894.375</span>

<span class="c17"> 062409,081237,-12,894.375</span>

<span class="c17"> 062409,081238,-13,894.375</span>

<span class="c17"> 062409,081239,-6,894.125</span>

<span class="c17"> 062409,081240,1,894.125</span>

<span class="c17"> 062409,081241,2,894.125</span>

<span class="c17"> 062409,081242,2,894.125</span>

<span class="c17"> 062409,081243,1,894.125</span>

<span class="c17"> 062409,081244,1,894.125</span>

<span class="c17"> …</span>

<span class="c17"></span>

<span class="c17"></span>

<span class="c1">The saved book may subsequently be used by any strategy for back testing and optimization purposes.</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Trading</span>

<span class="c1"></span>

<span class="c1">To begin live trading of your strategy, right-click (control-click on Mac) and choose Trade from the contextual menu.</span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image13.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"> </span>

<span class="c1">If JBookTrader detects that you connected to a real TWS account, it will ask for a confirmation:</span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 188.74px;">![](images/image14.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Reporting</span>

<span class="c1"></span>

<span class="c1">All major activities that JBookTrader performs during the trading day are logged into a dedicated log file in HTML format (EventLog.htm).  The event report can be used at the end of the trading day to diagnose and debug any potential problems that occurred during the day.</span>

<span class="c1"> </span>

<span class="c1">The event report typically looks like this:</span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 400.00px;">![](images/image15.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">The event report will contain informational messages from TWS and execution reports. If you see anything else (such as error messages from TWS or other errors), that would indicate a problem that needs to be addressed.</span>

<span class="c1"> </span>

<span class="c1">Additionally, every strategy creates its own log file, which is named after the class where this strategy is implemented. For example, if the strategy is defined in a class MyStrategy.java, the corresponding strategy report will be named MyStrategy.htm. The strategy log can be used to evaluate the strategy performance. Although the log is in HTML format for easy viewing, you can also import it into a spreadsheet (such as MS Excel) to analyze the strategy performance during the day and to construct the charts, if needed.</span>

<span class="c1"> </span>

<span class="c1">A typical example of a strategy log:</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 400.00px;">![](images/image16.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">The type of information logged in either event report and, like everything else in JBookTrader,  the strategy report can be customized by the user.</span>

<span class="c1"> </span>

<span class="c1"> </span>

<span class="c1"></span>

* * *

# <span class="c10 c27">Preferences</span>

<span class="c1"></span>

<span class="c0">JBookTrader user preferences are found by clicking the menu item Preferences in the Configure menu:</span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 298.25px;">![](images/image17.png)</span>

<span class="c0"></span>

<span class="c0"></span>

<span class="c0"></span>

<span class="c0"></span>

<span class="c1">The preferences window will appear:</span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 432.94px;">![](images/image2.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">The Preferences window has three tabs:</span>

*   <span class="c1">TWS Connection</span>
*   <span class="c1">Web Access</span>
*   <span class="c1">Look & Feel</span>

<span class="c1"> </span>

## <span class="c9">TWS Connection</span>

<a id="id.ce13eb40a137"></a>

<span class="c1"></span>

<span class="c1">The TWS Connection tab allows configuration of the host, port and client ID that should be used when connecting to TWS. The Host preference should match the IP Address or host name of the computer running the TWS instance you which JBookTrader to connect to.  Use localhost (the default) if TWS is running on the same machine as JBookTrader. The Port preference should match the value of the socket port as set in TWS under API in the Global Configuration (see Section 4, "Running JBookTrader"). The Client ID field should be used if you have multiple JBookTrader instances accessing the same TWS instance.  Each should connect with a unique Client ID.</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 432.94px;">![](images/image2.png)</span>

## <span class="c9">Web Access</span>

<span class="c1"></span>

<span class="c1">It is also possible to monitor JBookTrader via a web interface.  If enabled, JBookTrader’s in-built web server will provide basic information about a running strategy.</span>

<span class="c1"> </span>

<span class="c1">The configuration settings as they appear in the Web Access tab:</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 432.94px;">![](images/image3.png)</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span>To access the report from any web browser, connect to the IP address of the computer running JBookTrader and at the port configured in the Web Access preferences.  For example, if your machine is at IP address 192.168.1.68 and you’ve configured the Web Access port to 8,080, then, from your web browser, access http://192.168.1.68:8080/ for your report.</span> <span class="c29 c40 c14 c10">Note that web access is only available when JBookTrader is running in either "trade" or "forward test" mode.</span>

<span class="c29 c40 c14 c10"></span>

<span class="c1">To access your report, you'll be asked to reproduce the same credentials that were provided in the previous step.</span>

<span class="c1"></span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 425.00px; height: 203.00px;">![](images/image4.png)</span>

<span class="c1"></span>

<span class="c1">If the authentication challenge is successful, the report will be presented in the client web browser.  Here is an example of this report:</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 640.00px; height: 400.00px;">![](images/image5.png)</span>

<span class="c1"></span>

<span class="c1">Note, in this case, only the strategies "Equalizer" and "Simple" have generated a Strategy Report yet and clicking their names will open them.</span>

<span class="c1"></span>

<span class="c1">If Table Layout is set to "simple" then the output will be listed in alphabetical order without grouping by the underlying security.</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">If you’d like to access the report via a web browser on the same machine, simply use http://localhost:[port number]/.  If you’d like to monitor JBookTrader from any web browser anywhere on the Internet, you must ensure that your JBookTrader computer is accessible from the Internet with a known IP address or domain name.  This is beyond the scope of this document, but here are some pointers:</span>

<span class="c1"></span>

<span class="c1"></span>

<a id="t.06734fd04b187877576de884e739201b92a245d2"></a><a id="t.1"></a>

<table class="c46">

<tbody>

<tr class="c35">

<td class="c12" colspan="1" rowspan="1">

<span class="c10 c31">JBookTrader and your Browser are located...</span>

</td>

<td class="c12" colspan="1" rowspan="1">

<span class="c31 c10">Addressing notes</span>

</td>

</tr>

<tr class="c35">

<td class="c45" colspan="1" rowspan="1">

<span class="c24 c10">On the same machine</span>

</td>

<td class="c45" colspan="1" rowspan="1">

<span class="c24 c32">On most machines, you can access Web Access with this address:</span>

<span class="c24 c32"></span>

<span class="c24 c32">http://localhost:8080/ or</span>

<span class="c24 c32">http://127.0.0.1:8080/</span>

<span class="c24 c32">Assuming that you have configured the "Web access port" to 8080.  If this doesn't work for you, you may have a host-based firewall which is blocking this port.</span>

</td>

</tr>

<tr class="c35">

<td class="c45" colspan="1" rowspan="1">

<span class="c24 c10">On the same local network</span>

</td>

<td class="c45" colspan="1" rowspan="1">

<span class="c24 c32">This depends on your setup, but most consumer routers will assign an address from the following ranges for devices within your Local Area Network (LAN):</span>

<span class="c24 c32"></span>

<span class="c24 c32">http://10.xxx.xxx.xxx:8080/</span>

<span class="c24 c32">http://172.[16-31].xxx.xxx:8080/</span>

<span class="c24 c32">http://192.168.xxx.xxx:8080/</span>

<span class="c24 c32"></span>

<span class="c24 c32">Alternatively, you may be able to access your JBookTrader machine via its machine name as follows:</span>

<span class="c24 c32"></span>

<span class="c24 c32">http://[machine_name].local:8080/</span>

</td>

</tr>

<tr class="c35">

<td class="c45" colspan="1" rowspan="1">

<span class="c24 c10">Anywhere on the Internet</span>

</td>

<td class="c45" colspan="1" rowspan="1">

<span class="c24 c32">If you have enabled Net Address Translation (NAT, sometimes called "Games & Applications" in some consumer routers), on your Internet router, you may be able to access JBookTrader from anywhere in the world, if you know your public Internet IP address.</span>

<span class="c24 c32"></span>

<span class="c24 c32">http://[your public IP address]:[assigned NAT port]/</span>

<span class="c24 c32"></span>

<span class="c24 c32">Alternatively, if you use DNS, you could access JBookTrader with your host name as follows:</span>

<span class="c24 c32"></span>

<span class="c24 c32">http://[your hostname]:[assigned NAT port]/</span>

<span class="c24 c32">Note that the assigned NAT port may differ from your Web access port.</span>

</td>

</tr>

</tbody>

</table>

<span class="c24 c32"></span>

<span class="c24 c32"></span>

<span class="c1">In most modern browsers, the page will auto-update every 5 seconds or so to show the current statistics.</span>

<span class="c1"></span>

<span>Each strategy name is linked to the strategy report as shown in the</span> <span class="c7">Reporting</span><span class="c1"> section of this document.  In addition, the Event Report can be accessed by clicking the link "Event Report" at the bottom of the output.</span>

<span class="c1"></span>

<span class="c1"></span>

* * *

<a id="id.388f606ecd07"></a>

## <span class="c9">Look & Feel</span>

<span class="c1"></span>

<span class="c1">JBookTrader supports two main types of look & feel as shown in the Look & Feel preference tab:</span>

<span class="c1"></span>

<span class="c1"> </span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 600.00px; height: 380.00px;">![](images/image6.png)</span>

<span class="c1"></span>

<span class="c1">The two types are:</span>

<span class="c1"></span>

*   <span>Native - the native look & feel is provided by Sun and attempts to provide an interface for JBookTrader that is natural for the operating system it is running on.</span><span class="c39 c37 c32">  
    </span>
*   <span class="c1">Substance - this is a skinnable look & feel.  If this is selected, the Skin drop-menu will be active containing a number of color schemes.  Use of a Substance skin should provide a consistent look & feel across multiple running instances regardless of the operating system supporting them.</span>

<span class="c1"></span>

<span class="c1">Changing the skin will take effect immediately.  Changing the Look & Feel may require a restart before the change takes effect.</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Adding your own trading strategy</span>

<span class="c1"></span>

* * *

# <span class="c27 c10">Literature</span>

<span class="c1"></span>

<span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 240.00px; height: 240.00px;">![](images/image7.jpg)</span><span style="overflow: hidden; display: inline-block; margin: 0.00px 0.00px; border: 0.00px solid #000000; transform: rotate(0.00rad) translateZ(0px); -webkit-transform: rotate(0.00rad) translateZ(0px); width: 240.00px; height: 240.00px;">![](images/image8.jpg)</span>

<span class="c1">                                                                                                                                                     </span>

<span class="c1"></span>

<span class="c1"></span>

# <span class="c27 c10">Technical Support</span>

<span class="c1"></span>

<span>JBookTrader technical support is provided by the author and the users of this software in the </span><span class="c7">[JBookTrader Discussion Group](https://www.google.com/url?q=http://groups.google.com/group/jbooktrader/topics&sa=D&ust=1525081216115000)</span><span class="c1">.</span>

<span class="c1"></span>

<span class="c1"></span>

<span class="c1">■</span>