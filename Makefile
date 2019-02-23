HADOOPCP=$(shell hadoop classpath)
HIVECP=$(shell hadoop classpath | tr : '\n'  | grep hadoop-yarn | sed s:hadoop-yarn:hive: | tr '\n' :)

JAR=jar
JAVAC=javac

UDFs.jar: com/att/research/HiveUDF/UTCParse.class com/att/research/HiveUDF/ToSlippy.class
	$(JAR) fvc $@ $^

clean:
	rm -rf com UDFs.jar

com/att/research/HiveUDF/%.class: %.java
	$(JAVAC) -d . -cp $(HADOOPCP):$(HIVECP) $^

.PHONY: clean
