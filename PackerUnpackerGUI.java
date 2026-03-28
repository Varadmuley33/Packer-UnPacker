import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

class PackerUnpackerGUI extends JFrame implements ActionListener
{
    JButton bPack, bUnpack, bExecute;
    JTextField tFolder, tPackFile;
    JLabel lFolder, lPackFile, lTitle;

    String mode = "";

    ///////////////////////////////////////////////////////////
    // Constructor
    ///////////////////////////////////////////////////////////
    PackerUnpackerGUI()
    {
        setTitle("Packer Unpacker");
        setSize(500, 300);   // Bigger window
        setLayout(null);

        lTitle = new JLabel("Select Operation");
        lTitle.setBounds(180, 20, 200, 30);
        add(lTitle);

        bPack = new JButton("Pack");
        bPack.setBounds(100, 70, 120, 40);
        add(bPack);

        bUnpack = new JButton("Unpack");
        bUnpack.setBounds(260, 70, 120, 40);
        add(bUnpack);

        lFolder = new JLabel("Folder Name:");
        lFolder.setBounds(50, 140, 120, 30);
        lFolder.setVisible(false);
        add(lFolder);

        tFolder = new JTextField();
        tFolder.setBounds(180, 140, 200, 30);
        tFolder.setVisible(false);
        add(tFolder);

        lPackFile = new JLabel("Packed File Name:");
        lPackFile.setBounds(50, 180, 120, 30);
        lPackFile.setVisible(false);
        add(lPackFile);

        tPackFile = new JTextField();
        tPackFile.setBounds(180, 180, 200, 30);
        tPackFile.setVisible(false);
        add(tPackFile);

        bExecute = new JButton("Execute");
        bExecute.setBounds(180, 220, 120, 30);
        bExecute.setVisible(false);
        add(bExecute);

        bPack.addActionListener(this);
        bUnpack.addActionListener(this);
        bExecute.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    ///////////////////////////////////////////////////////////
    // Packing Logic (same)
    ///////////////////////////////////////////////////////////
    public static void Pack(String FolderName, String PackName) throws Exception
    {
        String Header = null;
        byte Key = 0x11;
        int iRet = 0;
        int i = 0, j = 0;

        byte Buffer[] = new byte[1024];
        byte bHeader[] = new byte[100];

        File fobj = new File(FolderName);

        if((fobj.exists()) && (fobj.isDirectory()))
        {
            File PackObj = new File(PackName);
            PackObj.createNewFile();

            FileOutputStream foobj = new FileOutputStream(PackObj);
            FileInputStream fiobj = null;

            File fArr[] = fobj.listFiles();

            for(i = 0; i < fArr.length; i++)
            {
                fiobj = new FileInputStream(fArr[i]);

                Header = fArr[i].getName() + " " + fArr[i].length();

                for(j = Header.length(); j < 100; j++)
                {
                    Header = Header + " ";
                }

                bHeader = Header.getBytes();

                foobj.write(bHeader,0,100);

                while((iRet = fiobj.read(Buffer)) != -1)
                {
                    for(j = 0; j < iRet ; j++)
                    {
                        Buffer[j] = (byte)(Buffer[j] ^ Key);
                    }

                    foobj.write(Buffer,0,iRet);
                }

                fiobj.close();
            }

            foobj.close();
        }
    }

    ///////////////////////////////////////////////////////////
    // Unpacking Logic (same)
    ///////////////////////////////////////////////////////////
    public static void Unpack(String PackName) throws Exception
    {
        int FileSize = 0;
        int i = 0;
        int iRet = 0;

        byte Key = 0x11;

        String Header = null;
        String Tokens[] = null;

        File fpackobj = new File(PackName);

        if(fpackobj.exists() == false)
        {
            JOptionPane.showMessageDialog(null, "No such packed file");
            return;
        }

        FileInputStream fiobj = new FileInputStream(fpackobj);

        byte bHeader[] = new byte[100];
        byte Buffer[] = null;

        while((iRet = fiobj.read(bHeader,0,100)) != -1)
        {
            Header = new String(bHeader);
            Header = Header.trim();

            Tokens = Header.split(" ");

            File fobj = new File(Tokens[0]);
            fobj.createNewFile();

            FileOutputStream foobj = new FileOutputStream(fobj);

            FileSize = Integer.parseInt(Tokens[1]);

            Buffer = new byte[FileSize];

            fiobj.read(Buffer,0,FileSize);

            for(i = 0; i < FileSize ; i++)
            {
                Buffer[i] = (byte)(Buffer[i] ^ Key);
            }

            foobj.write(Buffer,0,FileSize);

            foobj.close();
        }

        fiobj.close();
    }

    ///////////////////////////////////////////////////////////
    // Actions
    ///////////////////////////////////////////////////////////
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            if(e.getSource() == bPack)
            {
                mode = "pack";

                lFolder.setVisible(true);
                tFolder.setVisible(true);

                lPackFile.setVisible(true);
                tPackFile.setVisible(true);

                bExecute.setVisible(true);
            }
            else if(e.getSource() == bUnpack)
            {
                mode = "unpack";

                lFolder.setVisible(false);
                tFolder.setVisible(false);

                lPackFile.setVisible(true);
                tPackFile.setVisible(true);

                bExecute.setVisible(true);
            }
            else if(e.getSource() == bExecute)
            {
                if(mode.equals("pack"))
                {
                    Pack(tFolder.getText(), tPackFile.getText());
                    JOptionPane.showMessageDialog(this, "Packing Completed");
                }
                else if(mode.equals("unpack"))
                {
                    Unpack(tPackFile.getText());
                    JOptionPane.showMessageDialog(this, "Unpacking Completed");
                }
            }
        }
        catch(Exception obj)
        {
            JOptionPane.showMessageDialog(this, "Error: " + obj.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////
    // Main
    ///////////////////////////////////////////////////////////
    public static void main(String A[])
    {
        new PackerUnpackerGUI();
    }
}