import java.util.*;

class longestPathMain{
    public static void main(String[] args){
        int[][] ad = new int[11][10];
        int[] degree = new int[11];
        String e = "01 12 23 24 27 36 45 49 56 59 69 89";
        Scanner r = new Scanner(e);
        while (r.hasNextInt()){
            int a = r.nextInt();
            int u = a/10;
            int v = a%10;
            ad[u][degree[u]++] = v;
            ad[v][degree[v]++] = u;
        }

        longestPath md = new longestPath();
        int[] arr = new int[10];
        arr = md.longPathDAG(ad, degree, 10);
        for(int i = 0; i < 10; i++)
            System.out.println(arr[i]);
    }
}

public class longestPath {
    public longestPath(){

    }

    //인접리스트, 차수, 크기, 이미 사용한 점들 (chk가 true면 이미 쓰였으므로 무시) 을 받아
    //유사 최장경로를 딱 맞는 길이의 배열로 리턴한다.
    //reachable이 같이 들어오면 reachable한 루트를 선택한다.
    public int[] pseudoLongest(int[][] adjList, int[] degree, int N, boolean[] chk){
        int root = 0;
        for(;chk[root];root++);
        chk[root] = true;
        int[] way1 = new int[N];
        int length1 = 0;
        int[] way2 = new int[N];
        int length2 = 0;
        boolean brk;
        int look = root;
        while(true){
            brk = true;
            for(int i = 0; i < degree[look]; i++){
                if(chk[adjList[look][i]]) continue;
                chk[adjList[look][i]] = true;
                way1[length1] = adjList[look][i];
                length1++;
                look = adjList[look][i];
                brk = false;
                break;
            }
            if(brk) break;
        }
        look = root;
        while(true){
            brk = true;
            for(int i = 0; i < degree[look]; i++){
                if(chk[adjList[look][i]]) continue;
                chk[adjList[look][i]] = true;
                way2[length2] = adjList[look][i];
                length2++;
                look = adjList[look][i];
                brk = false;
                break;
            }
            if(brk) break;
        }
        int[] rtn = new int[length1+length2+1];
        for(int i = 0; i < length1; i++)
            rtn[i] = way1[length1-1-i];
        for(int i = 0; i < length2; i++)
            rtn[length1+1+i] = way2[i];
        rtn[length1] = root;
        return rtn;
    }
    public int[] pseudoLongest(int[][] adjList, int[] degree, int N, boolean[] chk, boolean[] reachable){
        int root = 0;
        for(;chk[root] || !reachable[root];root++);
        chk[root] = true;
        int[] way1 = new int[N];
        int length1 = 0;
        int[] way2 = new int[N];
        int length2 = 0;
        boolean brk;
        int look = root;
        while(true){
            brk = true;
            for(int i = 0; i < degree[look]; i++){
                if(chk[adjList[look][i]]) continue;
                chk[adjList[look][i]] = true;
                way1[length1] = adjList[look][i];
                length1++;
                look = adjList[look][i];
                brk = false;
                break;
            }
            if(brk) break;
        }
        look = root;
        while(true){
            brk = true;
            for(int i = 0; i < degree[look]; i++){
                if(chk[adjList[look][i]]) continue;
                chk[adjList[look][i]] = true;
                way2[length2] = adjList[look][i];
                length2++;
                look = adjList[look][i];
                brk = false;
                break;
            }
            if(brk) break;
        }
        int[] rtn = new int[length1+length2+1];
        for(int i = 0; i < length1; i++)
            rtn[i] = way1[length1-1-i];
        for(int i = 0; i < length2; i++)
            rtn[length1+1+i] = way2[i];
        rtn[length1] = root;
        return rtn;
    }

    public int[] longPathDAG(int[][] adjList, int[] degree, int N){
        boolean[] chk = new boolean[N];
        boolean[] reachable = new boolean[N];
        int used = 0;
        int temp = 0;
        int[] get;
        int[] rtn = new int[N];
        int start = 0;
        get = pseudoLongest(adjList, degree, N, chk);
        temp = get.length;
        for(int i = 0; i < temp; i++) {
            rtn[used + i] = get[i];
            chk[get[i]] = true;
            reachable[get[i]] = true;
            for(int k = 0; k < degree[get[i]]; k++)
                reachable[adjList[get[i]][k]] = true;
        }
        used += temp;
        while(used < N){
            get = pseudoLongest(adjList, degree, N, chk, reachable);
            temp = get.length;
            for(int i = 0; i < temp; i++) {
                if(reachable[get[i]]) {
                    rtn[used + i] = get[i];
                    chk[get[i]] = true;
                    reachable[get[i]] = true;
                    for(int k = 0; k < degree[get[i]]; k++)
                        reachable[adjList[get[i]][k]] = true;
                }
                else{
                    used--;
                    chk[get[i]] =false;
                }
            }
            used += temp;
        }
        return rtn;
    }
}
