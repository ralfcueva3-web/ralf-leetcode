import java.util.*;

class Solution {
    public int scoreOfParentheses(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(0);
            } else {
                int inner = stack.pop();
                int score = Math.max(2 * inner, 1);
                stack.push(stack.pop() + score);
            }
        }
        return stack.pop();
    }
}