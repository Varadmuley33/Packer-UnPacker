import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

class MarvellousPacker
{
    private String PackName;
    private String DirName;

    public MarvellousPacker(String A, String B)
    {
        this.PackName = A;
        this.DirName = B;
    }

    public String PackingActivity()
    {
        StringBuilder output = new StringBuilder();

        try
        {
            output.append("---------------------------------------------------------------\n");
            output.append("-------------Marvellous Packer Unpacker------------------------\n");
            output.append("---------------------------------------------------------------\n");
            output.append("-----------------Packing Activity------------------------------\n");
            output.append("---------------------------------------------------------------\n");

            int i = 0, j = 0, iRet = 0, iCountFile = 0;

            File fobj = new File(DirName);

            if (fobj.exists() && fobj.isDirectory())
            {
                output.append(DirName + " is successfully opened\n");

                File PackObj = new File(PackName);
                boolean bRet = PackObj.createNewFile();

                if (bRet == false)
                {
                    output.append("Unable to create pack file\n");
                    return output.toString();
                }

                File Arr[] = fobj.listFiles();
                FileOutputStream foobj = new FileOutputStream(PackObj);
                byte Buffer[] = new byte[1024];
                String Header = null;

                for (i = 0; i < Arr.length; i++)
                {
                    Header = Arr[i].getName() + " " + Arr[i].length();

                    for (j = Header.length(); j < 100; j++)
                    {
                        Header = Header + " ";
                    }

                    foobj.write(Header.getBytes());

                    FileInputStream fiobj = new FileInputStream(Arr[i]);

                    while ((iRet = fiobj.read(Buffer)) != -1)
                    {
                        foobj.write(Buffer, 0, iRet);
                    }

                    fiobj.close();
                    output.append("Packed file: " + Arr[i].getName() + "\n");
                    iCountFile++;
                }

                foobj.close();

                output.append("---------------------------------------------------------------\n");
                output.append("---------------Statistical Report------------------------------\n");
                output.append("Total files packed: " + iCountFile + "\n");
                output.append("---------------------------------------------------------------\n");
                output.append("----------Thank you for using our Application------------------\n");
            }
            else
            {
                output.append("ERROR: Directory does not exist\n");
            }
        }
        catch (Exception eobj)
        {
            output.append("ERROR: " + eobj.getMessage() + "\n");
        }

        return output.toString();
    }
}

public class PackerGUI extends JFrame
{
    private JTextField txtDirName;
    private JTextField txtPackName;
    private JTextArea txtOutput;
    private JButton btnPack;

    public PackerGUI()
    {
        setTitle("Marvellous Packer");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Marvellous Packer GUI", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

        txtDirName = new JTextField(25);
        txtPackName = new JTextField(25);
        btnPack = new JButton("Pack");

        inputPanel.add(new JLabel("Directory to Pack:"));
        inputPanel.add(txtDirName);
        inputPanel.add(new JLabel("Packed File Name:"));
        inputPanel.add(txtPackName);
        inputPanel.add(new JLabel());
        inputPanel.add(btnPack);

        add(inputPanel, BorderLayout.CENTER);

        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        add(new JScrollPane(txtOutput), BorderLayout.SOUTH);

        btnPack.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String dir = txtDirName.getText().trim();
                String pack = txtPackName.getText().trim();

                if (dir.equals("") || pack.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Please enter both directory and packed file name.");
                }
                else
                {
                    MarvellousPacker mobj = new MarvellousPacker(pack, dir);
                    String result = mobj.PackingActivity();
                    txtOutput.setText(result);

                    if (result.contains("Packed file"))
                    {
                        JOptionPane.showMessageDialog(null, "Directory packed successfully!");
                    }
                    else if (result.contains("ERROR: Directory does not exist"))
                    {
                        JOptionPane.showMessageDialog(null, "Directory not found. Please check the path.");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Something went wrong during packing.");
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
                PackerGUI gui = new PackerGUI();
                gui.setVisible(true);
            }
        });
    }
}
