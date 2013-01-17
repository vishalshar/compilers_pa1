JAVA=java
JAVAC=javac

PA1SRC := \
	Attr.java \
	SymbolTable.java \
	stline-tree.java \
	parser.java 

CLS := ${patsubst %.java,%.class,${PA1SRC}}

run: ${CLS}
	${JAVA} parser

${CLS}: ${PA1SRC}
	${JAVAC} ${PA1SRC}
	touch stline-tree.class

clean:
	rm -rf *.class
