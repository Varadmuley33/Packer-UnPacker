package MarvellousPackerUnpacker;

import java.io.*;
import java.util.*;

public class MarvellousUnPacker
{
    private String PackName;
    public MarvellousUnPacker(String A)
    {
        this.PackName = A;

    }

    public void UnPackingActivity()
    {
         try
        {
            File fobjnew = null;
            String Header = null;
            
            int Filesize = 0, iRet = 0 ,iCount = 0;


           

            File fobj = new File( PackName);


            // if packed file is not opend 
            if(! fobj.exists())
            {
                System.out.println("Unable to open packed file");
                return;

            }

            System.out.println(" Packed File is opend successfuly");

            FileInputStream fiobj = new FileInputStream(fobj);

            // buffer to read the buffer
            byte HeaderBuffer[] = new byte[100];


            // scan the packed file to extract the files from it 
            while ((iRet = fiobj.read(HeaderBuffer,0,100)) != -1)
            {
                 Header = new String(HeaderBuffer);

         

            Header = Header.trim();

            //Tokenize the header into two parts 

            String Tokens[] = Header.split(" ");

           
            fobjnew = new File(Tokens[0]);
            

            //create new file to extract 
            fobjnew.createNewFile();
         
            Filesize = Integer.parseInt(Tokens[1]);


            //Create new buffer to store files data 
            byte Buffer[] = new byte[Filesize];

            FileOutputStream foobj = new FileOutputStream(fobjnew);

            // Read the data from packed file 
            fiobj.read(Buffer,0,Filesize);


            // Write the data into extracted file
            foobj.write(Buffer,0,Filesize);

            System.out.println("File unpacked with name : "+ Tokens[0] +  " having size " + Filesize);

            iCount++;

            foobj.close();

            }
            fiobj.close();
        }

        catch (Exception eobj)
        {}

        
            
        }
    }