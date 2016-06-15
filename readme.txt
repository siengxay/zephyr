Coding exercise for Zephyr Health
Author: Siengxay Nhien siengxay@gmail.com

* INSTRUCTIONS TO BUILD AND RUN

Prerequisites:
-Oracle JDK1.8+
-Apache Maven 3.3.9

-Copy and unzip this file into your local machine into folder "zephyr-hcp-pub"
-go to that directory: cd ~/zephyr-hcp-pub
-to compile: mvn install
This will build a fat jar will dependencies under "target/zephyr-tophcp-jar-with-dependencies.jar"
-download the full XML file to your local folder, say into "zephyr-hcp-pub/data/pubmed_result.xml"
You will need to pass the path to that file as an argument when running the program
-to run: java -jar target/zephyr-tophcp-jar-with-dependencies.jar "data\pubmed_result.xml"
This should output the list of Top Health Care Providers, along with the number of publications
based on input XML File.

2016-06-06 11:52:51 INFO  TopHcp: William Bensinger-Seattle, WA: nPubs=12
2016-06-06 11:52:51 INFO  TopHcp: Leif Bergsagel-Scottsdale, AZ: nPubs=15
2016-06-06 11:52:51 INFO  TopHcp: Adam Cohen-Philadelphia, PA: nPubs=5
2016-06-06 11:52:51 INFO  TopHcp: Hermann Einsele-Germany: nPubs=23
2016-06-06 11:52:51 INFO  TopHcp: Amrita Krishnan-Duarte, CA: nPubs=4
2016-06-06 11:52:51 INFO  TopHcp: Edward Libby-Seattle, WA: nPubs=2
2016-06-06 11:52:51 INFO  TopHcp: Sagar Lonial-Atlanta, GA: nPubs=38
2016-06-06 11:52:51 INFO  TopHcp: Heinz Ludwig-Austria: nPubs=39
2016-06-06 11:52:51 INFO  TopHcp: Marφa-Victoria Mateos-Spain: nPubs=32
2016-06-06 11:52:51 INFO  TopHcp: Philip McCarthy-Buffalo: nPubs=13
2016-06-06 11:52:51 INFO  TopHcp: Vincent Rajkumar-Rochester, MN: nPubs=52
2016-06-06 11:52:51 INFO  TopHcp: Paul Richardson-Boston, MA: nPubs=91
2016-06-06 11:52:51 INFO  TopHcp: Jatin Shah-Houston, TX: nPubs=9
2016-06-06 11:52:51 INFO  TopHcp: Saad Usmani-Little Rock, AR: nPubs=11
2016-06-06 11:52:51 INFO  TopHcp: David Vesole-Hackensack, NJ: nPubs=8
2016-06-06 11:52:51 INFO  TopHcp: Ravi Vij-St. Louis, MO: nPubs=9
2016-06-06 11:52:51 INFO  TopHcp: Duration (ms)=70222

* CODE OVERVIEW
The main entry point of the code is the class com.snhien.zephyr.TopHcp
It reads the XML File as a stream and chunks it into individual publications
then run XPath expressions on each one of them, the assumption being that the
number of Top HCP is limited (fewer than 20 in this case).
