import java.util.*;

class KeywordLogic
{
    public static String[] list = {"==", "!=", ">", "<", "NOT", "AND", "OR" ,"<<", ">>"};

    public static boolean isDefined(String s)
    {
        return Arrays.asList(list).contains(s);
    }

    public static void execute(Interpreter intp, Lexer lexer, String token)
    {
        switch(token)
        {
            case "==":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    if(n1==n2) { intp.stack.push(1); }
                    else { intp.stack.push(0); }
                }
                break;

            case "!=":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    if(n1!=n2) { intp.stack.push(1); }
                    else { intp.stack.push(0); }
                }
                break;

            case ">":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    if(n1>n2) { intp.stack.push(1); }
                    else { intp.stack.push(0); }
                }
                break;

            case "<":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    if(n1<n2) { intp.stack.push(1); }
                    else { intp.stack.push(0); }
                }
                break;

            case "NOT":
                {
                    if(intp.stack.size()<1){ intp.setError("Stack empty"); return; }
                    
                    int n = intp.stack.pop();

                    if(n==0){ intp.stack.push(1); }
                    else { intp.stack.push(0); }
                    
                }
                break;

            case "AND":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    n2 = (n2==0) ? 0 : 1;

                    n1 = (n1==0) ? 0 : 1;

                    int res = n1 & n2;
                    
                    intp.stack.push(res); 
                }
                break;

            case "OR":
            {
                if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                
                int n2 = intp.stack.pop();

                int n1 = intp.stack.pop();

                n2 = (n2==0) ? 0 : 1;

                n1 = (n1==0) ? 0 : 1;

                int res = n1 | n2;
                
                intp.stack.push(res); 
            }
            break;

            case "<<":
                {
                    if(intp.stack.size()<1){ intp.setError("Stack empty"); return;}
                    
                    int n = intp.stack.pop();

                    n = n << 1 ;
                    
                    intp.stack.push(n); 
                }
                break;

            case ">>":
                {
                    if(intp.stack.size()<1){ intp.setError("Stack empty"); return;}
                    
                    int n = intp.stack.pop();

                    n = n >> 1 ;
                    
                    intp.stack.push(n); 
                }
                break;

            default: intp.setError("Unknow KeywordLogic"); break;
        }
    }
}

