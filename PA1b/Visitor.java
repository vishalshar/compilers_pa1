public interface Visitor {
    public void visit(Program p);
    public void visit(Stmt s);
    public void visit(assignStmt s);
    public void visit(printStmt s);
    public void visit(Expr e);
    public void visit(idExpr e);
    public void visit(opExpr e);
    public void visit(numExpr e);
    public void visit(eseqExpr e);
}
