import java.util.*;
class prtVisitor implements Visitor {
    static int num;
    static int PAD;
    private static String padding = "                                                                                ";
    
    prtVisitor() {
        num = 0;
        PAD = 0;
    }

    String pad(int n) { 
        if (n > 80) return padding;
        if (n < 0)  return "";
        return padding.substring(0,n);
    }
    public void visit(Program p) {
        p.label = "Node" + num++ + " (Program)";
        System.out.println( pad(PAD) + p.label);
        
        for (Stmt s: p.stl) { 
            PAD = PAD+4; 
            s.accept(this);
            PAD = PAD-4; 
        }
    }
    public void visit(Stmt s) {}
    public void visit(assignStmt s) {
        s.label = "Node" + num++ + "(assignStmt)";
        System.out.println(pad(PAD) + s.label);
        PAD = PAD + 4;
        s.label = "Node" + num++ + "(idExpr)-- not visiting";
        System.out.println(pad(PAD) + s.label);
        PAD = PAD - 4;
        Expr re = s.re;
        PAD = PAD + 4;
        re.accept(this);
        PAD = PAD - 4;
    }
    public void visit(printStmt s) {
        s.label = "Node" + num++ + "(printStmt)";
        System.out.println(pad(PAD) + s.label);
        LinkedList<Expr> exl = s.exl;
        for (Expr e:exl) {
            PAD = PAD + 4;
            e.accept(this);
            PAD = PAD - 4;
        }
    }
    public void visit(Expr e) {}
    public void visit(idExpr e) {
        e.label = "Node" + num++ + "(idExpr)";
        System.out.println(pad(PAD) + e.label);
    }
    public void visit(opExpr e) {
        e.label = "Node" + num++ + "(opExpr)";
        System.out.println(pad(PAD) + e.label);
        PAD = PAD + 4;
        e.eleft.accept(this);
        e.eright.accept(this);
        PAD = PAD - 4;
    }
    public void visit(numExpr e) {
        e.label = "Node" + num++ + "(numExpr)";
        System.out.println(pad(PAD) + e.label);
    }
    public void visit(eseqExpr e) {
        e.label = "Node" + num++ + "(eseqExpr)";
        System.out.println(pad(PAD) + e.label);
        PAD = PAD + 4;
        e.s.accept(this);
        e.e.accept(this);
        PAD = PAD - 4;
    }
}
