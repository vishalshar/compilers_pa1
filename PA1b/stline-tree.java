import java.util.*;
abstract class Stmt {
    int line;
    String label;
    void accept(Visitor v) { v.visit(this);}
}

class Program {
    LinkedList<Stmt> stl;
    String label;

    Program(LinkedList<Stmt> stl) { this.stl = stl;}
    void accept(Visitor v) { v.visit(this);}
}

class assignStmt extends Stmt {
    String id;
    idExpr ie;
    Expr re;

    assignStmt(int line, idExpr ie, Expr re) { 
        this.ie = ie; 
        this.re = re;
        this.id = ie.id;
    }
    void accept(Visitor v) { v.visit(this);}
}

class printStmt extends Stmt {
    LinkedList<Expr> exl;            

    printStmt(int line, LinkedList<Expr> exl) { this.exl = exl;}
    void accept(Visitor v) { v.visit(this);}
}

abstract class Expr {
    int line;
    String label;
    int val;
    void accept(Visitor v) { v.visit(this);}
}

class idExpr extends Expr {
    String id;
    Attr a;

    idExpr(int line, String id) {this.line = line; this.id = id;}
    void accept(Visitor v) { v.visit(this);}
}

class numExpr extends Expr {

    int n;
    numExpr(int line, int n) {this.n = n;}
    void accept(Visitor v) { v.visit(this);}
}

class opExpr extends Expr {
    Expr eleft, eright;
    public enum kind {PLUS, MINUS, STAR};
    kind k;

    opExpr(int line, Expr eleft, Expr eright, kind k) { this.eleft = eleft; this.eright = eright; this.k = k;}
    void accept(Visitor v) { v.visit(this);}
}

class eseqExpr extends Expr {
    Stmt s; Expr e;
    
    eseqExpr (int line, Stmt s, Expr e) { this.s = s; this.e = e;}
    void accept(Visitor v) { v.visit(this);}
}
