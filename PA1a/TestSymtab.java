import java.util.*;
public class TestSymtab {
  public static void main(String[] args) {
    SymbolTable<String, Attr> map = new SymbolTable<String,Attr>();
    Attr ai = new Attr(10);
    Attr aj = new Attr(20);

    map.enterScope();
    map.addId("i", ai);
    map.addId("j", aj);

    System.out.println((map.lookup("i") != null) ? "Yes" : "No");
    System.out.println((map.lookup("j") != null) ? "Yes" : "No");
    System.out.println((map.lookup("k") != null) ? "Yes" : "No");
    
    // Display symbol table
    map.display();
    map.exitScope();
                    
    }
}
    
