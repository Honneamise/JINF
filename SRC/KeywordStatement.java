import java.util.*;

class KeywordStatement
{
    public static String[] list = {"FUNCTION", "VARIABLE", ";", "DELETE", "DO", "LOOP","BEGIN", "UNTIL", "IF", "THEN", "ELSE", "->", "<-"};

    public static boolean isDefined(String s)
    {
        return Arrays.asList(list).contains(s);
    }

    public static void execute(Interpreter intp, Lexer lexer, String token)
    {
        switch(token)
        {
            case "FUNCTION":
                {
                    String func_name = lexer.get();

                    if(Keyword.isDefined(func_name)) { intp.setError("\"" + func_name + "\" is a Keyword !!!"); return; }

                    if(intp.variableDefined(func_name)) { intp.setError("\"" + func_name + "\" is a Variable !!!"); return; }

                    String ins = lexer.getUntil(";");

                    if(ins != null) { intp.functions.put(func_name, ins); }
                    else { intp.setError("Error reading function : "+ func_name + " (missing \";\" ?)"); }   
                }
                break;

            case "VARIABLE":
                {
                    String var_name = lexer.get();

                    if(Keyword.isDefined(var_name)) { intp.setError("\"" + var_name + "\" is a Keyword !!!"); return;}

                    if(intp.functionDefined(var_name)) { intp.setError("\"" + var_name + "\" is a Function !!!"); return; }

                    String end = lexer.get();

                    if(end == null || !end.equals(";") ) { intp.setError("Error reading variable : "+ var_name + " (missing \";\" ?)"); return; }

                    intp.variables.put(var_name, 0); 
                    
                }
                break;

                case "DELETE":
                {
                    String name = lexer.get();

                    if(Keyword.isDefined(name)) { intp.setError("\"" + name + "\" is a Keyword !!!"); return;}

                    if(intp.functionDefined(name)) { intp.functions.remove(name); return; }

                    if(intp.variableDefined(name)) { intp.variables.remove(name); return; }

                    intp.setError("Definition of \""+ name + "\" not found");
                }
                break;

            case "DO":
                {
                    if(intp.stack.size()<2){ intp.setError("Not enough elements on stack"); return;}
                    
                    int start = intp.stack.pop();

                    int end = intp.stack.pop();

                    String ins = lexer.getUntil("LOOP");

                    if(ins == null) { intp.setError("Error reading statement (missing \"LOOP\" ?)"); }
                    
                    for( int count = start; count < end; count++)
                    {
                        intp.execute(new Lexer(ins));
                    }

                }
                break;

            case "BEGIN":
                {
                    String ins = lexer.getUntil("UNTIL");

                    if(ins == null) { intp.setError("Error reading statement (missing \"UNTIL\" ?)"); }

                    int n = 0;

                    do 
                    {
                        intp.execute(new Lexer(ins));

                        if(intp.stack.size()<1){ intp.setError("Stack empty"); return;}

                        n = intp.stack.pop();
                    }
                    while ( n==0 );
                }
                break;

            case "IF":
                {
                    if(intp.stack.size()<1){ intp.setError("Stack empty"); return; }
                    
                    int logic = intp.stack.pop();

                    String ins = lexer.getUntil("THEN");

                    if(ins == null) { intp.setError("Error reading statement (missing \"THEN\" ?)"); }
                    
                    Lexer l = new Lexer(ins);

                    String _if = l.getUntil("ELSE");

                    String _else = null;

                    if( _if==null) { _if = ins; }
                    else{ _else=l.getUntilEnd(); }

                    if(logic==1){ intp.execute(new Lexer(_if));}
                    else
                    {
                        if( _else!=null){ intp.execute(new Lexer(_else));}
                    }
                }
                break;

            case "->":
                {
                    if(intp.stack.empty()){ intp.setError("Stack empty"); return; }
                    
                    String var_name = lexer.get();

                    if( !intp.variableDefined(var_name)) { intp.setError("Variable \"" + var_name + "\" not exist"); return; }   

                    int n = intp.stack.pop();

                    intp.variables.put(var_name, n); 
                }
                break;

            case "<-":
                {
                    String var_name = lexer.get();

                    if( !intp.variableDefined(var_name)) { intp.setError("Variable \"" + var_name + "\" not exist"); return; }   

                    Integer n = intp.variables.get(var_name);

                    intp.stack.push(n);

                }
                break;

            

            default: intp.setError("Unknow KeywordStatement"); break;

        }
    }
}