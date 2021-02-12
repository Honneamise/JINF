import java.util.*;

class KeywordStack 
{
    public static String[] list = {"DUP", "SWAP", "DROP"};

    public static boolean isDefined(String s)
    {
        return Arrays.asList(list).contains(s);
    }

    public static void execute(Interpreter intp, Lexer lexer, String token)
    { 
        switch(token)
        {
        
            case "DUP":
                {
                    if(intp.stack.empty()){ intp.setError("Stack empty"); return;}

                    intp.stack.push(intp.stack.peek());
                }
                break;

            case "SWAP":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    intp.stack.push(n2);

                    intp.stack.push(n1);
                }
                break;

            case "DROP":
                {
                    if(intp.stack.empty()){ intp.setError("Stack empty"); return;}

                    intp.stack.pop();
                }
                break;
            
            default: intp.setError("Unknow KeywordStack"); break;
            
        }
    }
}
