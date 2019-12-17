import java.io.*;
import java.util.*;

class j2k_main{
    public static void main(String[] args){
        String d, q, o;
        o = null;
        int n;
        d = args[0];
        q = args[1];
        n = Integer.parseInt(args[2]);
        boolean targetExist = false;
        if(args.length>3){
            targetExist = true;
            o = args[3];
        }

        j2k_io IO = new j2k_io(d, q, n, o);

        j2k_graph dg = IO.readData();
        j2k_graph[] qg = IO.readQuery();

        minDup md = new minDup();
        longestPath lp = new longestPath();

        int[] dag;

        IO.setOut("downorder.dag");
        for(int i = 0; i < n; i++){
            dag = md.rootedDownOrder(qg[i].adjList, qg[i].degree, qg[i].N);
            IO.write(dag);
        }
        IO.setOut("greedyLog.dag");
        for(int i = 0; i < n; i++){
            dag = md.greedyLog(qg[i].adjList, qg[i].degree, qg[i].N);
            IO.write(dag);
        }
        IO.setOut("longestPath.dag");
        for(int i = 0; i < n; i++){
            dag = lp.longPathDAG(qg[i].adjList, qg[i].degree, qg[i].N);
            IO.write(dag);
        }
    }
}

public class j2k_io {
    String data, query;
    int qnum;
    PrintStream out;
    public j2k_io(String datas, String querys, int qnums){
        data = datas;
        query = querys;
        qnum = qnums;
        out = System.out;
    }
    public j2k_io(String datas, String querys, int qnums, String outFile){
        data = datas;
        query = querys;
        qnum = qnums;
        if(outFile != null) {
            try {
                out = new PrintStream(new File(outFile));
            } catch (FileNotFoundException e) {
                out = System.out;
            }
        }else{
            out = System.out;
        }
    }

    public void setOut(String output){
        try {
            out = new PrintStream(new File(output));
        } catch (FileNotFoundException e){
            out = System.out;
        }
    }

    public j2k_graph readData(){
        try {
            Scanner in = new Scanner(new File(data));
            String line = in.nextLine();
            Scanner lineReader = new Scanner(line);
            lineReader.next();
            int temp = lineReader.nextInt();
            j2k_graph rtn = new j2k_graph(lineReader.nextInt());
            rtn.id = temp;
            temp = rtn.N;
            for (int i = 0; i < temp; i++) {
                line = in.nextLine();
                lineReader = new Scanner(line);
                lineReader.next();
                rtn.label[lineReader.nextInt()] = lineReader.nextInt();
            }
            int u, v;
            while (in.hasNextLine()) {
                line = in.nextLine();
                lineReader = new Scanner(line);
                lineReader.next();

                u = lineReader.nextInt();
                v = lineReader.nextInt();

                rtn.adjList[u][rtn.degree[u]++] = v;
                rtn.adjList[v][rtn.degree[v]++] = u;
            }
            return rtn;
        }catch (FileNotFoundException e){
            return null;
        }
    }

    public j2k_graph[] readQuery(){
        try {
            j2k_graph[] rtn = new j2k_graph[qnum];
            Scanner in = new Scanner(new File(query));
            String line;
            Scanner readLine;
            int id, v, e;
            int x, label, deg, vd;
            for (int q = 0; q < qnum; q++) {
                line = in.nextLine();
                readLine = new Scanner(line);
                readLine.next();
                id = readLine.nextInt();
                v = readLine.nextInt();
                e = readLine.nextInt();
                rtn[q] = new j2k_graph(v);
                rtn[q].id = id;
                for (int i = 0; i < v; i++) {
                    line = in.nextLine();
                    readLine = new Scanner(line);
                    x = readLine.nextInt();
                    label = readLine.nextInt();
                    deg = readLine.nextInt();
                    rtn[q].label[x] = label;
                    for (int d = 0; d < deg; d++) {
                        vd = readLine.nextInt();
                        rtn[q].adjList[x][rtn[q].degree[x]++] = vd;
                    }
                }
            }
            return rtn;
        } catch (FileNotFoundException e){
            return null;
        }
    }

    public void write(int[] dag){
        int n = dag.length;
        dag = makeValid(dag);
        for(int i = 0; i < n; i++){
            out.print(dag[i]);
            out.print(" ");
        }
        out.println();
    }

    //dag가 유효하지 않으면 강제로 0~n으로 바꾼다.
    public int[] makeValid(int[] dag){
        int n = dag.length;
        boolean[] chk = new boolean[n];
        boolean valid = true;
        for(int i = 0; i < n; i++){
            if (dag[i] < 0 || dag[i] >= n) {
                valid = false;
                break;
            }
            else if (chk[dag[i]]) {
                valid = false;
                break;
            }
            chk[dag[i]] = true;
        }
        if(!valid){
            int[] rtn = new int[n];
            for(int i = 0; i < n; i++)
                rtn[i] = i;
            return rtn;
        }
        return dag;
    }
}
