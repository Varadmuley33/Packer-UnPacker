import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

class MarvellousUnpacker
{
    private String PackName;

    public MarvellousUnpacker(String A)
    {
        this.PackName = A;
    }

    public String UnpackingActivity()
    {
        StringBuilder output = new StringBuilder();

        try
        {
            output.append("--------------------------------------------------------\n");
            output.append("----------- Marvellous Packer Unpacker -----------------\n");
            output.append("--------------------------------------------------------\n");
            output.append("----------------- UnPacking Activity -------------------\n");
            output.append("--------------------------------------------------------\n");

            String Header = null;
            File fobjnew = null;
            int FileSize = 0, iRet = 0, iCountFile = 0;

            File fobj = new File(PackName);

            if (!fobj.exists())
            {
                output.append("ERROR: Packed file not found!\n");
                return output.toString();
            }

            FileInputStream fiobj = new FileInputStream(fobj);

            byte HeaderBuffer[] = new byte[100];
            int length = 0;

            while ((length = fiobj.read(HeaderBuffer, 0, 100)) > 0)
            {
                String strHeader = new String(HeaderBuffer);
                String parts[] = strHeader.trim().split(" ");

                if (parts.length < 2)
                {
                    continue;
                }

                String filename = parts[0];
                FileSize = Integer.parseInt(parts[1]);

                byte FileData[] = new byte[FileSize];
                fiobj.read(FileData, 0, FileSize);

                fobjnew = new File(filename);
                FileOutputStream foobj = new FileOutputStream(fobjnew);
                foobj.write(FileData, 0, FileSize);
                foobj.close();

                output.append("Unpacked file: ").append(filename).append("\n");
                iCountFile++;
            }

            fiobj.close();

            output.append("--------------------------------------------------------\n");
            output.append("Total files unpacked: ").append(iCountFile).append("\n");
        }
        catch (Exception e)
        {
            output.append("ERROR: ").append(e.getMessage()).append("\n");
        }

        return output.toString();
    }
}

public class UnpackerGUI extends JFrame
{
    private JTextField txtFileName;
    private JTextArea txtOutput;
    private JButton btnUnpack;

    public UnpackerGUI()
    {
        setTitle("Marvellous Unpacker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Marvellous Unpacker GUI", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        txtFileName = new JTextField(25);
        btnUnpack = new JButton("Unpack");

        inputPanel.add(new JLabel("Packed File Name:"));
        inputPanel.add(txtFileName);
        inputPanel.add(btnUnpack);

        add(inputPanel, BorderLayout.CENTER);

        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        add(new JScrollPane(txtOutput), BorderLayout.SOUTH);

        btnUnpack.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String filename = txtFileName.getText().trim();

                if (filename.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Please enter a file name.");
                }
                else
                {
                    MarvellousUnpacker mobj = new MarvellousUnpacker(filename);
                    String result = mobj.UnpackingActivity();
                    txtOutput.setText(result);

                    if (result.contains("Unpacked file"))
                    {
                        JOptionPane.showMessageDialog(null, "File unpacked successfully!");
                    }
                    else if (result.contains("ERROR: Packed file not found"))
                    {
                        JOptionPane.showMessageDialog(null, "File not found. Please check the name and try again.");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Something went wrong during unpacking.");
                    }
                }
            }
        });
    }

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                UnpackerGUI gui = new UnpackerGUI();
                gui.setVisible(true);
            }
        });
    }
}
