import java.util.*;

class interpVisitor implements Visitor {
    SymbolTable<String, Attr> st;

    interpVisitor() {
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
      s.re.accept(this);
      s.ie.a = new Attr(s.re.val);
      st.addId(s.ie.id, s.ie.a);
    }
    public void visit(printStmt s) {
      for (Expr e:s.exl) {e.accept(this);}
      for (Expr e:s.exl) {System.out.printf("%d ", e.val);}
      System.out.printf("\n");
    }
    public void visit(Expr e) {}
    public void visit(idExpr e) {
      Attr a = st.lookup(e.id);
      if(a != null) e.val = a.v;
    }
    public void visit(opExpr e) {
      e.eleft.accept(this);
      e.eright.accept(this);

      switch(e.k) {
        case PLUS:  e.val = e.eleft.val + e.eright.val; break;
        case MINUS: e.val = e.eleft.val - e.eright.val; break;
        case STAR:  e.val = e.eleft.val * e.eright.val; break;
      }
    }
    public void visit(numExpr e) { e.val = e.n; }
    public void visit(eseqExpr ex) {
      ex.s.accept(this);
      ex.e.accept(this);
      ex.val = ex.e.val;
    }
}
