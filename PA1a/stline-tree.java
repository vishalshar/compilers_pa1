import java.util.*;
abstract class Stmt {
    int line;
    void semant(SymbolTable<String,Attr> stab) {}
    void interp(SymbolTable<String,Attr> stab) {}
}

class Program {
    LinkedList<Stmt> stl;

    Program(LinkedList<Stmt> stl) { this.stl = stl;}

    void semant(SymbolTable<String,Attr> stab) {
        stab.enterScope();
        for (Stmt s: stl) { s.semant(stab);}
    }
    void interp(SymbolTable<String,Attr> stab) {
        for (Stmt s: stl) { s.interp(stab); }
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

    void interp(SymbolTable<String,Attr> stab) {
        re.interp(stab);
        ie.a = new Attr(re.val);
        stab.addId(ie.id, ie.a);
    }
}

class printStmt extends Stmt {
    LinkedList<Expr> exl;

    printStmt(int line, LinkedList<Expr> exl) { this.exl = exl;}
    void semant(SymbolTable<String,Attr> stab) {
        for (Expr e:exl) { e.semant(stab);}
    }

    void interp(SymbolTable<String,Attr> stab) {
      for (Expr e:exl) { e.interp(stab); }
      for (Expr e:exl) { System.out.printf("%d ", e.val); }
      System.out.printf("\n");
    }
}

abstract class Expr {
    int line;
    void semant(SymbolTable<String,Attr> stab) {}
    void interp(SymbolTable<String,Attr> stab) {}
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

    void interp(SymbolTable<String,Attr> stab) {
       a = stab.lookup(id);
       if(a != null) val = a.v;
    }
}

class numExpr extends Expr {

    int n;
    numExpr(int line, int n) {this.n = n;}
    void semant(SymbolTable<String,Attr> stab) { val = n;}
    void interp(SymbolTable<String,Attr> stab) { val = n; }
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

    void interp(SymbolTable<String,Attr> stab) {
        eleft.interp(stab);
        eright.interp(stab);
        switch (k) {
            case PLUS:  val = eleft.val + eright.val;
                        break;
            case MINUS: val = eleft.val - eright.val;
                        break;
            case STAR:  val = eleft.val * eright.val;
                        break;
            default:    val = 0;
        }

    }
}

class eseqExpr extends Expr {
    Stmt s; Expr e;

    eseqExpr (int line, Stmt s, Expr e) { this.s = s; this.e = e;}
    void semant(SymbolTable<String,Attr> stab) {
        s.semant(stab);
        e.semant(stab);
    }

    void interp(SymbolTable<String,Attr> stab) {
        s.interp(stab);
        e.interp(stab);
        val = e.val;
    }
}
