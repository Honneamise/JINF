import java.util.*;

class KeywordMath 
{
    public static String[] list = {"+","-","*","/","++","--","MOD","ABS","NEG","MIN","MAX"};

    public static boolean isDefined(String s)
    {
        return Arrays.asList(list).contains(s);
    }

    public static void execute(Interpreter intp, Lexer lexer, String token)
    {
        switch(token)
        {
            case "+":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    intp.stack.push(n1+n2);
                }
                break;

            case "-":
                {   
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    intp.stack.push(n1-n2);
                }
                break;

            case "*":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    intp.stack.push(n1*n2);
                }
                break;

            case "/":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    intp.stack.push(n1/n2);
                }
                break;

            case "++":
                {
                    String var_name = lexer.get();

                    if( !intp.variableDefined(var_name)) { intp.setError("Variable \"" + var_name + "\" not exist"); return; }   

                    Integer n = intp.variables.get(var_name);

                    intp.variables.put(var_name, ++n);
                }
                break;

            case "--":
                {
                    String var_name = lexer.get();

                    if( !intp.variableDefined(var_name)) { intp.setError("Variable \"" + var_name + "\" not exist"); return; }   

                    Integer n = intp.variables.get(var_name);

                    intp.variables.put(var_name, --n);

                }
                break;


            case "MOD":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    intp.stack.push(n1%n2);
                }
                break;

            case "ABS":
                {
                    if(intp.stack.size()<1){ intp.setError("Stack empty"); return;}
                    
                    int n = intp.stack.pop();

                    intp.stack.push(Math.abs(n));
                }
                break;

            case "NEG":
                {
                    if(intp.stack.size()<1){ intp.setError("Stack empty"); return;}
                    
                    int n = intp.stack.pop();

                    intp.stack.push(-1*n);
                }
                break;

            case "MIN":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    intp.stack.push(Math.min(n1,n2));
                }
                break;

            case "MAX":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int n2 = intp.stack.pop();

                    int n1 = intp.stack.pop();

                    intp.stack.push(Math.max(n1,n2));
                }
                break;

            default: intp.setError("Unknow KeywordMath"); break;
        }
    }
}
