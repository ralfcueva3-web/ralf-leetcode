class Solution {
    private int i = 0; 

    public List<String> braceExpansionII(String expr) {
        Set<String> result = parse(expr);
        List<String> list = new ArrayList<>(result);
        Collections.sort(list);
        return list;
    }

    private Set<String> parse(String expr) {
        Set<String> result = new TreeSet<>();
        Set<String> current = new TreeSet<>(List.of(""));

        while (i < expr.length()) {
            char c = expr.charAt(i);

            if (c == '{') {
                i++; 
                Set<String> inner = parse(expr); 
                current = cartesian(current, inner);
            } else if (c == '}') {
                i++; 
                break; 
            } else if (c == ',') {
                i++; 
                result.addAll(current); 
                current = new TreeSet<>(List.of("")); 
            } else {

                Set<String> letter = new TreeSet<>(List.of(String.valueOf(c)));
                current = cartesian(current, letter);
                i++;
            }
        }

        result.addAll(current); 
        return result;
    }

    private Set<String> cartesian(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>();
        for (String x : a)
            for (String y : b)
                result.add(x + y);
        return result;
    }
}