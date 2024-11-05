import java.io.*;
import java.util.*;

public class pass1{
    private String[][] mnt;
    private String[][] mdt;
    private String[][] ala;
    private int mntc,mdtc,alac,ctr;
    private boolean m,x;


    pass1(){
        mnt= new String[5][4];
        mnt[0][1]="MNT";
        mdt=new String[15][2];
        mdt[0][1]="MDT";
        ala=new String[15][2];
        ala[0][1]="ALA";
        mntc=mdtc=alac=ctr=1;
        x=m=false;
    }

    public void pass(String ifile, String ofile)throws IOException{
        try(BufferedReader r=new BufferedReader(new FileReader(ifile))){
            String line;
            while((line=r.readLine())!=null)
            {
                String[] token=line.split("\\s+");
                macro_def(token);
            }
            print_all(ofile);
        }  
    }
    private void macro_def(String[] a){
        if(a[0].equals("MACRO"))
        {
            x=m=true;
        }
        else{
        if(m){
            
            if(x){
                String[] arg=arg_sep(a[1]);
            mnt[mntc][0]=String.valueOf(mntc);
            mnt[mntc][1]=a[0];  
            mnt[mntc][2]=String.valueOf(mdtc);
            mnt[mntc][3]=String.valueOf(alac);
            mntc++;
            for(int i=0;i<arg.length;i++)
            {
                ala[alac][0]=String.valueOf(alac);
                ala[alac][1]=arg[i];
                alac++;
            }
            x=false;}
            else if(a[0].equals("MEND"))
        {
            mdt[mdtc][0]=String.valueOf(mdtc);
            mdt[mdtc][1]=a[0];
                mdtc++;
            m=false;
        }
            else{
                mdt[mdtc][0]=String.valueOf(mdtc);
                mdt[mdtc][1]=a[0]+" "+to_mdt(a[1]);
                mdtc++;
            }
            

        }
    }}
    private String[] arg_sep(String a){
        String[]s=a.split("\\,");
    return s;
    }
   private String to_mdt(String x){
    String t="";
    String[] arr=arg_sep(x);
    for(int j=0;j<arr.length;j++){
    for(int i=Integer.parseInt(mnt[ctr][3]);i<alac;i++)   //replace 10 by ALA counter
    {
        if(arr[j].equals(ala[i][1]))
        {
            t=t+"#"+ala[i][0];            
        t=t+",";}
    }}
    return t;
   }

   private void print_all(String ofile)throws IOException{
    try(FileWriter w=new FileWriter(ofile)){
    String t;
    
    
    for(int i=0;i<mntc;i++)
    {
        t=mnt[i][0]+"\t"+mnt[i][1]+"\t"+mnt[i][2]+"\t"+mnt[i][3];
        System.out.println(t);
        w.write(t);
        w.write(System.lineSeparator());
    }
    System.out.println();
    
    for(int i=0;i<mdtc;i++)
    {
        t=mdt[i][0]+"\t"+mdt[i][1];
        System.out.println(t);
        w.write(t);
        w.write(System.lineSeparator());
    }System.out.println();
    
    for(int i=0;i<alac;i++)
    {
        t=ala[i][0]+"\t"+ala[i][1];
        System.out.println(t);
        w.write(t);
        w.write(System.lineSeparator());
    }
     System.out.println();
    w.close();
   }
   }

    public static void main(String args[]){
        if(args.length!=2)
        {
            System.out.println("pass1.java <inputfile> <outputfile>");
        }
        pass1 macro=new pass1();
        try
        {macro.pass(args[0],args[1]);}
        catch(IOException e){
            System.err.println("error:"+e.getMessage());
        }

    }
}