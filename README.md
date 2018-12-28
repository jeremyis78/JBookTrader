# JBookTrader [![Build Status](https://travis-ci.org/jeremyis78/JBookTrader.svg?branch=master)](https://travis-ci.org/jeremyis78/JBookTrader)

This project is my attempt at merging JBookTrader and JSystemTrader to help practice 
my software architecture/design skills. I want to be able to run candle-based
(OHLCV) trading strategies as well as the already supported market-depth-based
strategies in one project. There are some fundamental differences between the two 
projects and therein lies the challenge to overcome so both types of strategies can
be traded under a single portfolio manager. After that's done, I also want to add 
the ability to automate option trading strategies based on technical analysis
indicators/IV Rank to help remove my emotions from trading them.

JBookTrader is a fully automated trading system (ATS) that can trade various
types of market securities during the trading day without user monitoring. All
aspects of trading, such as obtaining market prices, analyzing price patterns,
making trading decisions, placing orders, monitoring order executions, and 
controlling the risk are automated according to the user preferences. The 
central idea behind JBookTrader is to completely remove emotions from trading, 
so that the trading system can systematically and consistently follow a 
predefined set of rules.

The features include strategy back testing, optimization, market data recording,
and real time trading via the Interactive Brokers API.

JBookTrader is written in Java and is intended for software developers. It is
not an "off-the-shelf" product that can be installed and run. Instead,
JBookTrader provides a framework for developing automated trading systems and
requires a certain amount of programming knowledge and experience in Java. If
you are not a software developer or if you don't have much experience
programming in Java, JBookTrader is probably not for you.

Before you start working with JBookTrader, please read the 
[JBookTrader User Guide](
https://github.com/jeremyis78/JBookTrader/tree/master/docs/UserGuide) 
and the documents located in the /docs directory of the distribution.

![Back Testing Screenshot](docs/backtest.png "Back Testing")
![Charting Screenshot](docs/chart.png "Charting")
![Optimizing Visualization](docs/optMap2.png "Optimizing Visualization")


### Development Cycle  
For new development after JBookTrader-9.01 (the last official release), it 
can now be built with java8 and mvn.
The following assumes JDK8 and mvn are installed

Using custom classpath (target/classes and lib/ directory)
```bash
mvn clean compile
```
```bash
./run/JBookTrader.sh
```  
  
Using jar:
```bash
mvn clean package
```  
```bash
./run/JBookTraderJar.sh
```

NOTES
 1) for both run scripts, you can attach your IDE's debugger on port 5005
 2) If all new development used JBookTraderJar.sh there's no
    need for the lib/ directory anymore.
