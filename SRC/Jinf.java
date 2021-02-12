import java.nio.file.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.*;

class Jinf extends JFrame implements ActionListener, CaretListener 
{
    private static final long serialVersionUID = 0;

    JTextArea caret_area = null;
    JScrollPane caret_pane = null;

    JTextArea code_area = null;
    JScrollPane code_pane = null;

    JPanel editor_panel = null;

    JTextArea output_area = null;
    JScrollPane output_pane = null;
    JSplitPane io_panel = null;

    JTextArea functions_area = null;
    JScrollPane functions_pane = null;
    JTextArea variables_area = null;
    JScrollPane variables_pane = null;
    JTextArea stack_area = null;
    JScrollPane stack_pane = null;
    JPanel viewer_panel = null;

    JMenuItem file_load = null;
    JMenuItem file_load_and_execute = null;
    JMenuItem file_save = null;
    JMenu file_menu = null;

    JMenuItem intp_clear_input = null;
    JMenuItem intp_clear_output = null;
    JMenuItem intp_reset = null;
    JMenuItem intp_execute_all = null;
    JMenu intp_menu = null;

    JMenuItem help_keywords = null;
    JMenuItem help_examples = null;
    JMenu help_menu = null;

    JMenuBar menu_bar = null;

    JTextArea help_area = null;
    JScrollPane help_pane = null;
    JFrame help_frame = null;

    JTextArea examples_area = null;
    JScrollPane examples_pane = null;
    JFrame examples_frame = null;

    Interpreter intp = null;

    Jinf() 
    {
        // default font
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 16);

        // bordo
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        // frame stuff
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("JINF");

        setPreferredSize(new Dimension(1024, 768));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // code area
        code_area = new JTextArea("# to execute the current line use SHIFT + ENTER");

        code_area.setFont(font);

        code_area.addCaretListener(this);

        setInputAreaBindings();

        code_pane = new JScrollPane(code_area);

        code_pane.setBorder(BorderFactory.createTitledBorder(border, "Code"));

        //caret area
        caret_area = new JTextArea("0",1,3);

        caret_area.setFont(font);

        caret_area.setBackground(Color.LIGHT_GRAY);

        caret_area.setForeground(Color.BLACK);

        caret_area.setEditable(false);

        DefaultCaret caret = (DefaultCaret)caret_area.getCaret();

        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        
        updateCaretArea();

        caret_pane = new JScrollPane(caret_area, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

        caret_pane.getVerticalScrollBar().setModel(code_pane.getVerticalScrollBar().getModel());

        caret_pane.setBorder(BorderFactory.createTitledBorder(border, "Line"));

        //code panel
        editor_panel = new JPanel();

        editor_panel.setLayout( new BorderLayout() );

        editor_panel.add(caret_pane,BorderLayout.WEST);

        editor_panel.add(code_pane,BorderLayout.CENTER);

        // output area
        output_area = new JTextArea();

        output_area.setFont(font);

        output_area.setBackground(Color.BLACK);

        output_area.setForeground(Color.WHITE);

        output_area.setEditable(false);

        output_pane = new JScrollPane(output_area);

        output_pane.setBorder(BorderFactory.createTitledBorder(border, "Output"));

        // io panel
        io_panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editor_panel, output_pane);
        
        io_panel.setDividerLocation(500);

        // functions area
        functions_area = new JTextArea();

        functions_area.setFont(font);

        functions_area.setBackground(Color.DARK_GRAY);

        functions_area.setForeground(Color.WHITE);

        functions_area.setEditable(false);

        functions_pane = new JScrollPane(functions_area);

        functions_pane.setPreferredSize(new Dimension(150, 150));

        functions_pane.setBorder(BorderFactory.createTitledBorder(border, "Functions"));

        //variables area
        variables_area = new JTextArea();

        variables_area.setFont(font);

        variables_area.setBackground(Color.DARK_GRAY);

        variables_area.setForeground(Color.WHITE);

        variables_area.setEditable(false);

        variables_pane = new JScrollPane(variables_area);

        variables_pane.setPreferredSize(new Dimension(150, 150));

        variables_pane.setBorder(BorderFactory.createTitledBorder(border, "Variables"));

        // stack area
        stack_area = new JTextArea();

        stack_area.setFont(font);

        stack_area.setBackground(Color.DARK_GRAY);

        stack_area.setForeground(Color.WHITE);

        stack_area.setEditable(false);

        stack_pane = new JScrollPane(stack_area);

        stack_pane.setPreferredSize(new Dimension(150, 150));

        stack_pane.setBorder(BorderFactory.createTitledBorder(border, "Stack"));

        // viewer panel
        viewer_panel = new JPanel();

        viewer_panel.setLayout(new BoxLayout(viewer_panel, BoxLayout.PAGE_AXIS));

        viewer_panel.add(functions_pane);

        viewer_panel.add(variables_pane);

        viewer_panel.add(stack_pane);

        // menu
        file_load = new JMenuItem("Load");
        file_load.addActionListener(this);

        file_load_and_execute = new JMenuItem("Load and execute");
        file_load_and_execute.addActionListener(this);

        file_save = new JMenuItem("Save");
        file_save.addActionListener(this);

        file_menu = new JMenu("File");
        file_menu.add(file_load);
        file_menu.add(file_load_and_execute);
        file_menu.add(file_save);

        intp_clear_input = new JMenuItem("Clear input");
        intp_clear_input.addActionListener(this);

        intp_clear_output = new JMenuItem("Clear output");
        intp_clear_output.addActionListener(this);

        intp_reset = new JMenuItem("Reset");
        intp_reset.addActionListener(this);

        intp_execute_all = new JMenuItem("Execute all");
        intp_execute_all.addActionListener(this);

        intp_menu = new JMenu("Interpreter");
        intp_menu.add(intp_clear_input);
        intp_menu.add(intp_clear_output);
        intp_menu.add(intp_reset);
        intp_menu.add(intp_execute_all);

        help_keywords = new JMenuItem("Keywords");
        help_keywords.addActionListener(this);

        help_examples = new JMenuItem("Examples");
        help_examples.addActionListener(this);

        help_menu = new JMenu("Help");
        help_menu.add(help_keywords); 
        help_menu.add(help_examples); 

        menu_bar = new JMenuBar();
        menu_bar.add(file_menu);
        menu_bar.add(intp_menu);
        menu_bar.add(help_menu);
        //menu_bar.add(Box.createHorizontalGlue());

        // add elements to frame
        setJMenuBar(menu_bar);

        add(viewer_panel, BorderLayout.WEST);

        add(io_panel, BorderLayout.CENTER);

        pack();

        setLocationRelativeTo(null);

        setVisible(true);

        //help frame
        String help_content = null;

        try 
        {
            help_content = new String(Files.readAllBytes(Paths.get("./RES/keywords.txt")));
        } catch (Exception exc) { JOptionPane.showMessageDialog(null, "Unable to open keywords file"); }

        help_area = new JTextArea(help_content);//recuperare il testo
        help_area.setFont(font);
        help_area.setLineWrap(true);
        help_area.setBackground(Color.DARK_GRAY);
        help_area.setForeground(Color.WHITE);
        help_area.setEditable(false);

        help_pane = new JScrollPane(help_area);
        help_pane.setBorder(BorderFactory.createTitledBorder(border, "Keywords"));

        help_frame = new JFrame();

        help_frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        help_frame.setTitle("JINF Keywords");

        help_frame.setPreferredSize(new Dimension(400, 800));

        help_frame.setLayout(new BorderLayout());

        help_frame.add(help_pane,BorderLayout.CENTER);

        help_frame.pack();

        help_frame.setLocationRelativeTo(null);//????

        help_frame.setVisible(false);


        //help frame
        String examples_content = null;

        try 
        {
            examples_content = new String(Files.readAllBytes(Paths.get("./RES/examples.txt")));
        } catch (Exception exc) { JOptionPane.showMessageDialog(null, "Unable to open example file"); }

        examples_area = new JTextArea(examples_content);//recuperare il testo
        examples_area.setFont(font);
        examples_area.setLineWrap(true);
        examples_area.setBackground(Color.DARK_GRAY);
        examples_area.setForeground(Color.WHITE);
        examples_area.setEditable(false);

        examples_pane = new JScrollPane(examples_area);
        examples_pane.setBorder(BorderFactory.createTitledBorder(border, "Examples"));

        examples_frame = new JFrame();

        examples_frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        examples_frame.setTitle("JINF Examples");

        examples_frame.setPreferredSize(new Dimension(800, 800));

        examples_frame.setLayout(new BorderLayout());

        examples_frame.add(examples_pane,BorderLayout.CENTER);

        examples_frame.pack();

        examples_frame.setLocationRelativeTo(null);//????

        examples_frame.setVisible(false);

        // core element
        intp = new Interpreter();

    }

    public int getCaretLineIndex()
    {
        int index = 0;

        try
        {
            int caret_pos = code_area.getCaretPosition();

            String raw_line = code_area.getText(0, caret_pos);

            for (int i = 0; i < raw_line.length(); i++) 
            {
                if (raw_line.charAt(i) == '\n') { index++; }
            }

        }catch(Exception e) { index = 0; }

        return index;
    }

    public String getCaretLine() 
    {
        String line = null;

        try 
        {
            int index = getCaretLineIndex();

            int start = code_area.getLineStartOffset(index);
            int end = code_area.getLineEndOffset(index);
            line = code_area.getText(start, end - start);

            if (line.length() == 0) { return null; }

        } catch (Exception exc) { return null; }

        return line;
    }

    public void setInputAreaBindings() 
    {
        InputMap input = code_area.getInputMap();

        ActionMap actions = code_area.getActionMap();

        //shift enter
        KeyStroke shift_enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK);

        input.put(shift_enter, "SHIFT ENTER");

        actions.put("SHIFT ENTER", new AbstractAction() 
            {
                private static final long serialVersionUID = 10;

                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    executeLine();
                }
            }
        );

    }

    public void updateCaretArea()
    {
        int count = code_area.getLineCount();

        String content = "";

        for(int i=0;i<count;i++)
        {
            content += i+"\n";
        }

        content = content.substring(0,content.length()-1);

        caret_area.setText(content);

        //hilight current line
        try
        {
            int index = getCaretLineIndex();//index of code area

            DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);

            int start = caret_area.getLineStartOffset(index);
            int end = caret_area.getLineEndOffset(index);

            caret_area.getHighlighter().removeAllHighlights();

            caret_area.getHighlighter().addHighlight(start, end, painter);

        }catch(Exception e){}


    }

    public void updateFunctionsArea() 
    {
        String content = "";

        for (String s : intp.functions.keySet()) 
        {
            content += s + "\n";
        }

        functions_area.setText(content);
    }

    public void updateVariablesArea() 
    {
        String content = "";

        for (String s : intp.variables.keySet()) 
        {
            content += s + "\n";
        }

        variables_area.setText(content);
    }

    public void updateStackArea() 
    {
        String content = "";

        Integer[] arr = intp.stack.toArray(new Integer[intp.stack.size()]);

        for (int i = arr.length - 1; i >= 0; i--) {
            content += arr[i] + "\n";
        }

        stack_area.setText(content);

        stack_area.setCaretPosition(0);
    }

    public void executeLine() 
    {
        int line_index = getCaretLineIndex();

        String line = getCaretLine();

        if (line == null || line.length() == 0) { return; }

        intp.clearIO();

        intp.execute(new Lexer(line));

        if (intp.err != null) 
        {
            output_area.append("[ERR] "+ line_index + " : " + intp.err+"\n");
        } 
        else 
        {
            if(intp.out!=null && intp.out.length()!=0) { output_area.append(intp.out+"\n"); }
        }

        updateFunctionsArea();
        updateVariablesArea();
        updateStackArea();
    }

    public void executeCode() 
    {
        String code = code_area.getText();

        if (code == null || code.length() == 0) { return; }

        String lines[] = code.split("\r|\n|\r\n");

        for (int i = 0; i < lines.length; i++) 
        {
            intp.clearIO();

            intp.execute(new Lexer(lines[i]));

            if (intp.err != null) 
            {
                output_area.append("[ERR] "+ i + " : " + intp.err+"\n" );
                break;
            } 
            else 
            {
                if(intp.out!=null && intp.out.length()!=0) { output_area.append(intp.out+"\n"); }
            }

            updateFunctionsArea();
            updateVariablesArea();
            updateStackArea();
            output_area.setCaretPosition(output_area.getDocument().getLength());
        }

    }

    @Override
    public void caretUpdate(CaretEvent e) 
    {
        try
        {
            updateCaretArea();
        
        }catch(Exception exc){}
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        /* sezione file */

        // load
        if (e.getSource() == file_load) 
        {
            JFileChooser fc = new JFileChooser();
            fc.setApproveButtonText("Load");

            int res = fc.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) 
            {
                String filename = fc.getSelectedFile().getAbsolutePath();

                String content = "";
                try {
                    content = new String(Files.readAllBytes(Paths.get(filename)));
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "Unable to open file");
                }

                code_area.setText(content);
                code_area.setCaretPosition(0);

                updateCaretArea();
            }
        }

        // load and run
        if (e.getSource() == file_load_and_execute) 
        {
            JFileChooser fc = new JFileChooser();
            fc.setApproveButtonText("Load and execute");

            int res = fc.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) 
            {
                String filename = fc.getSelectedFile().getAbsolutePath();

                String content = "";
                try 
                {
                    content = new String(Files.readAllBytes(Paths.get(filename)));
                } catch (Exception exc) 
                {
                    JOptionPane.showMessageDialog(null, "Unable to open file");
                }

                code_area.setText(content);
                code_area.setCaretPosition(0);

                updateCaretArea();

                executeCode();
            }
        }

        // save
        if (e.getSource() == file_save) 
        {
            JFileChooser fc = new JFileChooser();
            fc.setApproveButtonText("Save");

            int res = fc.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) 
            {
                String filename = fc.getSelectedFile().getAbsolutePath();

                String content = code_area.getText();
                try 
                {
                    Files.writeString(Paths.get(filename), content);
                } catch (Exception exc) 
                {
                    JOptionPane.showMessageDialog(null, "Unable to save file");
                }

            }
        }

        /* sezione intp */

        // clear code area
        if (e.getSource() == intp_clear_input) 
        {
            code_area.setText("");
            updateCaretArea();
        }

        //clear output area
        if (e.getSource() == intp_clear_output) 
        {
            output_area.setText("");
        }

        // reset
        if (e.getSource() == intp_reset) 
        {
            intp.clearIO();
            intp.reset();
            updateFunctionsArea();
            updateVariablesArea();
            updateStackArea();
        }

        // execute all
        if (e.getSource() == intp_execute_all) 
        {
            executeCode();
        }

        /* sezione help */
        
        //help keywords
        if (e.getSource() == help_keywords) 
        {
            help_frame.setVisible(true);
        }

        //examples
        if (e.getSource() == help_examples) 
        {
            examples_frame.setVisible(true);
        }

    }

}


