import java.util.*;
abstract class Stmt {
    int line;
    void semant(SymbolTable<String,Attr> stab) {}
}

class Program {
    LinkedList<Stmt> stl;

    Program(LinkedList<Stmt> stl) { this.stl = stl;}

    void semant(SymbolTable<String,Attr> stab) {
        stab.enterScope();
        for (Stmt s: stl) { s.semant(stab);}
    }
    void interp() {
        // add code
    }
}

class assignStmt extends Stmt {
    idExpr ie;
    Expr re;

    assignStmt(int line, idExpr ie, Expr re) { 
        this.ie = ie; 
        this.re = re;
    }
    void semant(SymbolTable<String,Attr> stab) {
        if (stab.lookup(ie.id) != null) {
            System.err.println("Line " + line);
            System.err.println("ERROR01: only single assignment allowed " + ie.id);
        }
        else {
            Attr atmp = new Attr(0);
            stab.addId(ie.id, atmp);
            ie.a = atmp;
        }
        re.semant(stab);
    }
}

class printStmt extends Stmt {
    LinkedList<Expr> exl;            

    printStmt(int line, LinkedList<Expr> exl) { this.exl = exl;}
    void semant(SymbolTable<String,Attr> stab) {
        for (Expr e:exl) { e.semant(stab);}
    }
}

abstract class Expr {
    int line;
    void semant(SymbolTable<String,Attr> stab) {}
    int val;  // for interp()
}

class idExpr extends Expr {
    String id;
    Attr a;

    idExpr(int line, String id) {this.line = line; this.id = id;}
    void semant(SymbolTable<String,Attr> stab) {
        Attr a = stab.lookup(id);
        if (a == null) {
            System.err.println("Line " + line);
            System.err.println("ERROR02: variable not defined " + id);
        }
    }
}

class numExpr extends Expr {

    int n;
    numExpr(int line, int n) {this.n = n;}
    void semant(SymbolTable<String,Attr> stab) { val = n;}
}

class opExpr extends Expr {
    Expr eleft, eright;
    public enum kind {PLUS, MINUS, STAR};
    kind k;

    opExpr(int line, Expr eleft, Expr eright, kind k) { 
        this.eleft = eleft; this.eright = eright; this.k = k;
    }
    void semant(SymbolTable<String,Attr> stab) {
        eleft.semant(stab);
        eright.semant(stab);
    }
}

class eseqExpr extends Expr {
    Stmt s; Expr e;
    
    eseqExpr (int line, Stmt s, Expr e) { this.s = s; this.e = e;}
    void semant(SymbolTable<String,Attr> stab) {
        s.semant(stab);
        e.semant(stab);
    }
}
