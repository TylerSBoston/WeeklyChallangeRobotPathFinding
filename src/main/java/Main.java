import java.util.LinkedList;

public class Main {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		RobotPathFinder pathTester = new RobotPathFinder();
			
			// Junit would be better but......
			// standard test
			try {
				System.out.println("standard test");
				System.out.println(pathTester.findPath(new int[] {4,4}, new LinkedList<Integer[]>()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("long test");
				System.out.println(pathTester.findPath(new int[] {10,4}, new LinkedList<Integer[]>()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("tall test");
				System.out.println(pathTester.findPath(new int[] {4,10}, new LinkedList<Integer[]>()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("Blocked  test");
				LinkedList<Integer[]> blockers = new LinkedList<Integer[]>();
				blockers.add(new Integer[]{2,2});
				blockers.add(new Integer[]{1,1});
				blockers.add(new Integer[]{1,2});
				blockers.add(new Integer[]{2,1});
				System.out.println(pathTester.findPath(new int[] {4,4}, blockers));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				System.out.println("impossible  test");
				LinkedList<Integer[]> blockers = new LinkedList<Integer[]>();
				blockers.add(new Integer[]{2,2});
				blockers.add(new Integer[]{1,1});
				blockers.add(new Integer[]{1,0});
				blockers.add(new Integer[]{1,3});
				System.out.println(pathTester.findPath(new int[] {4,4}, blockers));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				System.out.println("Dead end  test");
				LinkedList<Integer[]> blockers = new LinkedList<Integer[]>();
				blockers.add(new Integer[]{0,4});
				blockers.add(new Integer[]{1,4});
				blockers.add(new Integer[]{2,4});
				blockers.add(new Integer[]{2,3});
				blockers.add(new Integer[]{1,3});
				System.out.println(pathTester.findPath(new int[] {5,10}, blockers));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}

}
