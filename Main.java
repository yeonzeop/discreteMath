import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    static class Graph{
        ArrayList<ArrayList<Integer>> adjm;//인접행렬
        int vertex=0;
        int count=0;
        boolean[] visited;
        //인접행렬 공간 생성
        public Graph(int v) {
            vertex=v;
            adjm=new ArrayList<>(v);
            visited=new boolean[this.vertex];
        }
        //무가중치
        public void writeMat(String row){
            count++;
            ArrayList<Integer> r=new ArrayList<>(vertex);
            for(int i=1;i<=vertex;i++){
               if(count!=i && row.contains(Integer.toString(i)))
                   r.add(1);
               else r.add(0);
           }
            adjm.add(r);
        }
        //가중치
        public void writeMat2(String[] vertexs,String[] values){
            count++;
            int n=0;
            ArrayList<Integer> r=new ArrayList<>(this.vertex);
            for(int i=1;i<=vertex;i++){
                if(count!=i && Arrays.asList(vertexs).contains(Integer.toString(i)))
                    r.add(Integer.parseInt(values[n++]));
                else if (count==i) {
                    r.add(0);
                } else r.add(Integer.MAX_VALUE);
            }
            adjm.add(r);
        }
    }

    static ArrayList<Graph> createGraphData1(String f) {
        ArrayList<Graph> data = null;
        try {
            Scanner scan = new Scanner(new File(f));
            data = new ArrayList<>();
            while (scan.hasNext()) {
                int v = scan.nextInt();//정점 개수
                scan.nextLine();
                Graph g = new Graph(v);
                for (int i = 0; i < v; i++) {
                    String row = scan.nextLine();
                    g.writeMat(row);
                }
                data.add(g);
            }
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
        }
        return data;
    }

    static ArrayList<Graph> createGraphData2(String f) {
        ArrayList<Graph> data = null;
        try {
            Scanner scan = new Scanner(new File(f));
            data = new ArrayList<>();
            while (scan.hasNext()) {
                int v = scan.nextInt();//정점 개수
                scan.nextLine();
                Graph g = new Graph(v);
                for (int i = 0; i < v; i++) {
                    String row = scan.nextLine();
                    String[] nums=row.split(" ");
                    String[] vertexs=new String[(int)((nums.length-1)/2)];
                    String[] values=new String[(int)((nums.length-1)/2)];
                    int n=0;
                    int k=0;
                    for(int j=1;j<=(int)((nums.length-1)/2);j++){
                        vertexs[n++]=nums[2*j-1];
                        values[k++]=nums[2*j];
                    }
                    g.writeMat2(vertexs,values);
                }
                data.add(g);
            }
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
        }
        return data;
    }

    static void BFS(Graph g) {
        for (int i = 0; i < g.vertex; i++) {
            g.visited[i] = false;
        }

        ArrayList<Integer> visitVertex = new ArrayList<>();
        visitVertex.add(0);
        g.visited[0] = true;
        System.out.print(1);

        for (int i = 0; i < visitVertex.size(); i++) {
            int v = visitVertex.get(i);

            for (int j = 1; j < g.vertex; j++) {
                if (g.adjm.get(v).get(j) == 1 && !(g.visited[j])) {
                    g.visited[j] = true;
                    visitVertex.add(j);
                    System.out.print(" - " + (j + 1));
                }
            }
        }

    }
    static void DFS(Graph g){
        for(int i=0;i<g.vertex;i++){
            g.visited[i]=false;
        }
        g.visited[0]=true;
        System.out.print(1);
        for(int i=1;i<g.vertex;i++){
            if(g.adjm.getFirst().get(i)==1&&!(g.visited[i])){
                DFS(g,i);
            }
        }
        System.out.println();
    }
    static void DFS(Graph g,int v){
        g.visited[v]=true;
        System.out.print(" - "+(v+1));
        for(int i=0;i<g.vertex;i++){
            if(g.adjm.get(v).get(i)==1&&!(g.visited[i])){
                DFS(g,i);
            }
        }
    }
    static void traversal(ArrayList<Graph> data){
        int i=1;
        for(var g:data){
            System.out.println();
            System.out.println("그래프 ["+(i++)+"]");
            System.out.println("-".repeat(50));
            System.out.println("깊이 우선 탐색");
            DFS(g);
            System.out.println("너비 우선 탐색");
            BFS(g);
            System.out.println();
            System.out.println("=".repeat(50));
        }
        System.out.println();
        System.out.println();
    }
    static void Dijkstra(ArrayList<Graph> data){
        int i=1;
        for(var g:data){
            System.out.println();
            System.out.println("그래프 ["+(i++)+"]");
            Dijkstra(g);
            System.out.println();
        }
    }
    static void Dijkstra(Graph g){
        ArrayList<Integer> s=new ArrayList<>();
        int[] distance=new int[g.vertex];
        ArrayList<ArrayList<Integer>> path=new ArrayList<>();

        s.add(1);
        for (int i = 0; i < g.vertex; i++) {
            distance[i] = g.adjm.getFirst().get(i);
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(1);
            path.add(arr);
        }


        while(s.size()!=g.vertex) {
            int min=1;
            for (int i = 1; i < g.vertex; i++) {
                if(s.contains(i+1))
                    continue;
                else min=i;
                for(int j=0;j<g.vertex;j++){
                    if(!s.contains(j+1)&&(distance[j]<distance[min])){
                        min=j;
                    }
                }
                break;
            }

            s.add(min+1);
            path.get(min).add(min+1);

            for(int i=0;i<g.vertex;i++){
                int compare=distance[min]+g.adjm.get(min).get(i);
                if((distance[i]>compare) && compare>0){
                    distance[i]=compare;
                    path.get(i).clear();
                    for(int j=0;j<path.get(min).size();j++){
                        path.get(i).add(path.get(min).get(j));
                    }
                }
            }
        }

        System.out.println("-".repeat(50));
        System.out.println("시작점: "+1);
        for(int i=1;i<g.vertex;i++){
            System.out.print("정점 "+"["+(i+1)+"]: "+1);
            for(int j=1;j<path.get(i).size();j++){
                System.out.print(" - "+path.get(i).get(j));
            }
            System.out.println(", 길이 : "+distance[i]);
        }
        System.out.println("=".repeat(50));
        System.out.println();
    }
    public static void main(String[] args) {
//        ArrayList<Graph> graphs1=createGraphData1("src/input1.txt");
        ArrayList<Graph> graphs2=createGraphData2("src/input2.txt");
//        System.out.println("1. 그래프 탐방 수행 결과");
//        traversal(graphs1);
        System.out.println("2. 최단 경로 구하기 수행 결과");
        Dijkstra(graphs2);

    }
}
