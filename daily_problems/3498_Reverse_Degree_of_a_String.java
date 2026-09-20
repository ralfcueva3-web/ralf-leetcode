class Solution {
    public int reverseDegree(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            int reversedAlpha = 27 - (s.charAt(i) - 'a' + 1);
            int stringPos = i + 1;
            result += reversedAlpha * stringPos;
        }
        return result;
    }
}