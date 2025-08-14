import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

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

            File fobj = new File(DirName);
            if (fobj.exists() && fobj.isDirectory())
            {
                output.append(DirName + " is successfully opened\n");

                File PackObj = new File(PackName);
                if (!PackObj.createNewFile())
                {
                    output.append("Unable to create pack file\n");
                    return output.toString();
                }

                File Arr[] = fobj.listFiles();
                FileOutputStream foobj = new FileOutputStream(PackObj);
                byte Buffer[] = new byte[1024];
                String Header;

                for (File file : Arr)
                {
                    Header = file.getName() + " " + file.length();
                    while (Header.length() < 100)
                    {
                        Header += " ";
                    }

                    foobj.write(Header.getBytes());

                    FileInputStream fiobj = new FileInputStream(file);
                    int iRet;
                    while ((iRet = fiobj.read(Buffer)) != -1)
                    {
                        foobj.write(Buffer, 0, iRet);
                    }
                    fiobj.close();
                }
                foobj.close();
                output.append("Packing completed successfully.\n");
            }
            else
            {
                output.append("Directory not found.\n");
            }
        }
        catch (Exception e)
        {
            output.append("Exception occurred: " + e.getMessage() + "\n");
        }
        return output.toString();
    }
}

class MarvellousUnpacker
{
    private String PackFile;

    public MarvellousUnpacker(String A)
    {
        this.PackFile = A;
    }

    public String UnpackingActivity()
    {
        StringBuilder output = new StringBuilder();
        try
        {
            output.append("---------------------------------------------------------------\n");
            output.append("-------------Marvellous Packer Unpacker------------------------\n");
            output.append("---------------------------------------------------------------\n");
            output.append("-----------------Unpacking Activity----------------------------\n");
            output.append("---------------------------------------------------------------\n");

            FileInputStream fiobj = new FileInputStream(PackFile);
            byte Header[] = new byte[100];
            int iRet;

            while ((iRet = fiobj.read(Header, 0, 100)) > 0)
            {
                String str = new String(Header).trim();
                String[] words = str.split(" ");
                String filename = words[0];
                long size = Long.parseLong(words[1]);

                FileOutputStream foobj = new FileOutputStream(filename);
                byte Buffer[] = new byte[1024];
                long remaining = size;

                while (remaining > 0)
                {
                    int read = fiobj.read(Buffer, 0, (int)Math.min(Buffer.length, remaining));
                    if (read == -1) break;
                    foobj.write(Buffer, 0, read);
                    remaining -= read;
                }
                foobj.close();
            }
            fiobj.close();
            output.append("Unpacking completed successfully.\n");
        }
        catch (Exception e)
        {
            output.append("Exception occurred: " + e.getMessage() + "\n");
        }
        return output.toString();
    }
}

public class PackerUnpacker extends JFrame implements ActionListener
{
    JButton packButton, unpackButton;
    JTextArea outputArea;

    public PackerUnpacker()
    {
        super("Marvellous Packer Unpacker");

        packButton = new JButton("Pack Files");
        unpackButton = new JButton("Unpack Files");
        outputArea = new JTextArea(20, 50);
        outputArea.setEditable(false);

        packButton.addActionListener(this);
        unpackButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(packButton);
        panel.add(unpackButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == packButton)
        {
            String dirName = JOptionPane.showInputDialog(this, "Enter Directory Name:");
            String packName = JOptionPane.showInputDialog(this, "Enter Pack File Name:");

            MarvellousPacker obj = new MarvellousPacker(packName, dirName);
            outputArea.setText(obj.PackingActivity());
        }
        else if (ae.getSource() == unpackButton)
        {
            String packFile = JOptionPane.showInputDialog(this, "Enter Pack File Name:");

            MarvellousUnpacker obj = new MarvellousUnpacker(packFile);
            outputArea.setText(obj.UnpackingActivity());
        }
    }

    public static void main(String[] args)
    {
        new PackerUnpacker();
    }
}
