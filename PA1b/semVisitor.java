import java.util.*;
class semVisitor implements Visitor {
    SymbolTable<String, Attr> st;
    
    semVisitor() {
        st = new SymbolTable<String, Attr>();
        st.enterScope();
    }

    public void visit(Program p) {
        for (Stmt s: p.stl) { 
            s.accept(this);
        }
        st.exitScope();
    }
    public void visit(Stmt s) {}
    public void visit(assignStmt s) {
        String id = s.id;
        if (st.lookup(id) != null) {
            System.err.println("Line " + s.line);
            System.err.println("ERROR01: only single assignment allowed " + id);
        }
        else {
            Attr atmp = new Attr(0);
            st.addId(id, atmp);
            s.ie.a = atmp;
        }
        // for lhs, not really visiting
        Expr re = s.re;
        re.accept(this);
    }
    public void visit(printStmt s) {
       LinkedList<Expr> exl = s.exl;
        for (Expr e:exl) {
            e.accept(this);
        }
    }
    public void visit(Expr e) {}
    public void visit(idExpr e) {
        String id = e.id;
        Attr a = st.lookup(id);
        if (a == null) {
            System.err.println("Line " + e.line);
            System.err.println("ERROR02: variable not defined " + id);
        }
        else e.a = a;
    }
    public void visit(opExpr e) {
        e.eleft.accept(this);
        e.eright.accept(this);
    }
    public void visit(numExpr e) { }
    public void visit(eseqExpr e) {
        e.s.accept(this);
        e.e.accept(this);
    }
}
