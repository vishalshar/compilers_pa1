import java.util.*;
class parser {


    public static void main(String[] args) {
        Program root;
        int line;
        // a:= 5 + 3;
        line = 1;
        numExpr n1 = new numExpr(line,5);
        numExpr n2 = new numExpr(line,3);
        opExpr  o1 = new opExpr(line, n1, n2, opExpr.kind.PLUS);
        idExpr il1 = new idExpr(line,"a");
        assignStmt s1 = new assignStmt(line, il1, o1);

        // b:= (print(a, a-1), 10*a);
        line = 2;
        LinkedList<Expr> el1 = new LinkedList<Expr>();
        idExpr i1 = new idExpr(line,"a");
        el1.addLast((Expr) i1);
        numExpr n3 = new numExpr(line,1);
        idExpr  i2 = new idExpr(line,"a");
        opExpr  o2 = new opExpr(line, i2, n3, opExpr.kind.MINUS);
        el1.addLast((Expr) o2);
        printStmt p1 = new printStmt(line, el1);
        //                     10*a
        numExpr n4 = new numExpr(line,10);
        idExpr  i3 = new idExpr(line,"a");
        opExpr  o3 = new opExpr(line, i3, n4, opExpr.kind.STAR);
        eseqExpr e1 = new eseqExpr(line, (Stmt) p1, (Expr) o3);
        idExpr il2 = new idExpr(line,"b");
        assignStmt s2 = new assignStmt(line, il2, (Expr) e1);

        // print (b);
        line = 3;
        idExpr i4 = new idExpr(line, "b");
        LinkedList<Expr> el2 = new LinkedList<Expr>();
        el2.addLast( (Expr) i4);
        printStmt s3 = new printStmt(line, el2);

        // c = x + a;
        line = 4;
        idExpr il3 = new idExpr(line, "c");
        idExpr i5 = new idExpr(line, "x");
        idExpr i6 = new idExpr(line, "a");
        opExpr o4 = new opExpr(line, i5, i6, opExpr.kind.PLUS);
        assignStmt s4 = new assignStmt(line, il3, (Expr) o4);

        LinkedList<Stmt> sl = new  LinkedList<Stmt>();
        sl.addLast(s1);
        sl.addLast(s2);
        sl.addLast(s3);
        //sl.addLast(s4);

        root = new Program(sl);
        SymbolTable<String,Attr> st = new SymbolTable<String,Attr>();
        root.semant(st);
        root.interp(st);
    }
}
