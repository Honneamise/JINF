import java.util.*;

class Lexer 
{
    String [] tokens = null;
    int cur_token = 0;

    Lexer(String s)
    {
        if (s == null) { tokens = new String[0]; return; }

        tokens = s.split(" |\t|\r|\n|\r\n");

        ArrayList<String> list = new ArrayList<String>();

        for(int i=0;i<tokens.length;i++)
        {
            if( tokens[i]!=null && tokens[i].length()!=0 && !tokens[i].equals("") ) { list.add(tokens[i]); }
        }

        tokens = list.toArray(new String[list.size()]);
    }

    public String get()
    {
        if(cur_token==tokens.length){ return null; }

        String token = tokens[cur_token];

        cur_token++;

        return token;
    }

    public String getUntil(String delim)
    {
        String res = "";

        while(!empty() && !peek().equals(delim))
        {
            res += get() +" ";
        }

        if(empty()){ return null; }

        if(res.length()>0)
        {
            res = res.substring(0, res.length()-1);
        }

        get();//advance delimiter
        
        return res;
    }

    public String getUntilEnd()
    {
        String res = "";

        while(!empty())
        {
            res += get() +" ";
        }

        return res;
    }

    public String peek()
    {
        if(empty()){ return null; }
        return tokens[cur_token];
    }

    public boolean empty()
    {
        return cur_token==tokens.length;
    }
}

