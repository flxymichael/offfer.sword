package BinaryTree;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BinaryTree {


    //从上到下打印树,无需分层
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList result = new ArrayList(100);

        if (root == null) {
            return result;
        }

        ConcurrentLinkedDeque<TreeNode> queue = new ConcurrentLinkedDeque<TreeNode>();
        queue.add(root);

        while (queue.size() > 0) {

            TreeNode t = queue.remove();
            result.add(t.val);


            if (t.left != null) {
                result.add(root.left.val);
                queue.add(root.left);
            }
            if (t.right != null) {
                result.add(root.right.val);
                queue.add(root.right);
            }

        }
        return result;

    }


    //按层打印树
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        if (pRoot == null) {
            return result;
        }

        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(pRoot);


        while (queue.size() > 0) {

            int size = queue.size();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();

            while (size-- > 0) {
                TreeNode t = queue.pop();
                arrayList.add(t.val);
                if (t.left != null) {
                    queue.add(t.left);
                }
                if (t.right != null) {
                    queue.add(t.right);
                }
            }
            result.add(arrayList);
        }
        return result;
    }

    //按之字型 打印树

    public ArrayList<ArrayList<Integer>> Printzhi(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (pRoot == null) {
            return result;
        }

        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(pRoot);
        int level = 1;

        while (queue.size() > 0) {

            int size = queue.size();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();

            while (size-- > 0) {
                TreeNode t = queue.removeFirst();
                if (t != null) {
                    if (t.left != null) {
                        queue.add(t.left);
                    }

                    if (t.right != null) {
                        queue.add(t.right);
                    }
                    arrayList.add(t.val);
                }
            }

            if (level % 2 == 0) {
                Collections.reverse(arrayList);
            }
            result.add(arrayList);
            level++;
        }
        return result;
    }

    //是否对称树
    public boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) {
            return true;
        }
        return sym(pRoot.left, pRoot.right);
    }

    public boolean sym(TreeNode l, TreeNode r) {
        if (l == null && r == null) {
            return true;
        }
        if (l == null || r == null) {
            return false;
        }
        if (l.val != r.val) {
            return false;
        }
        return sym(l.left, r.right) && sym(l.right, r.left);
    }


    //二叉搜索树中第K小的值
    ////中序遍历儿茶搜索树，就是从小到大排列的结果
    int countK = 0;

    public TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot != null) {
            TreeNode node1 = KthNode(pRoot.left, k);
            if (node1 != null) return node1;
            if (++countK == k) return pRoot;
            TreeNode node2 = KthNode(pRoot.right, k);
            if (node2 != null) return node2;

        }
        return null;
    }

    //二叉树的深度
    //01 分治法   DFS?
    public int TreeDepth(TreeNode root) {
        if (root == null) return 0;
        int levelLeft = TreeDepth(root.left);
        int levelRight = TreeDepth(root.right);
        return Math.max(levelLeft, levelRight) + 1;//啊哈
    }

    //02 层次遍历
    public int TreeDepth02(TreeNode root) {
        int level = 0;
        LinkedList<TreeNode> linkedList = new LinkedList<>();
        if (root == null) {
            return 0;
        }
        linkedList.add(root);
        while (linkedList.size() > 0) {
            int flag = linkedList.size();
            while (flag-- > 0) {
                TreeNode t = linkedList.pop();
                if (t.left != null) {
                    linkedList.add(t.left);
                }
                if (t.right != null) {
                    linkedList.add(t.right);
                }
            }
            level++;
        }
        return level;
    }

    //根据先序遍历和中序遍历重建二叉树
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        if (pre.length != in.length) {
            return null;
        }
        TreeNode root = new TreeNode(pre[0]);
        for (int i = 0; i < in.length; i++) {
            if (pre[0] == in[i]) {
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return root;
    }

    /*
    是否为子树
     */
    //先序遍历树root1中的每个节点 nroot1，找到root1中节点值与root2相等的那个节点
    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return false;
        }
        boolean nodeRoot, nodeLeft, nodeRight;
        //此处相当于树root1中的第一个节点（根节点已经找到），所以直接调用recur函数
        nodeRoot = recur(root1, root2);
        nodeLeft = recur(root1.left, root2);
        nodeRight = recur(root1.right, root2);

        return nodeRoot || nodeLeft || nodeRight;
    }

    //判断树root1中 以nroot1为根节点的子树 是否包含树root2
    private boolean recur(TreeNode a, TreeNode b) {
        //b都遍历完了，说明确实是子树
        if (b == null) {
            return true;
        }
        //a遍历完了，而b还没遍历完，说明不是
        if (a == null) {
            return false;
        }

        if (a.val != b.val) {
            return false;
        }

        return recur(a.left, b.left) && recur(a.right, b.right);
    }

    //输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
    // 如果是则返回true,否则返回false。假设输入的数组的任意两个数字都互不相同。
    public static boolean VerifySequenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;
        return Verify(sequence, 0, sequence.length - 1);
    }

    public static boolean Verify(int[] sequence, int start, int end) {
        if (start >= end) return true;
        int root = sequence[end];
        int i = start;
        while (sequence[i] < root) {
            i++;
        }
        int flag = i;
        for (; i < end; i++) {
            if (sequence[i] < root) return false;
        }
        return Verify(sequence, start, flag - 1) && Verify(sequence, flag, end - 1);
    }

    //输入一棵二叉树，判断该二叉树是否是平衡二叉树。
    //在这里，我们只需要考虑其平衡性，不需要考虑其是不是排序二叉树
    //平衡二叉树（Balanced Binary Tree），具有以下性质：
    // 它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。

    //后序遍历
    public boolean IsBalanced_Solution(TreeNode root) {
        return getDepth(root) != -1;
    }

    public int getDepth(TreeNode node) {
        if (node == null) return 0;
        int l = getDepth(node.left);
        if (l == -1) return -1;
        int r = getDepth(node.right);
        if (r == -1) return -1;
        int flag = Math.abs(getDepth(node.left) - getDepth(node.right));
        if (flag > 1) return -1;
        return Math.max(getDepth(node.left), getDepth(node.right)) + 1;
    }


}




