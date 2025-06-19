import java.util.ArrayList;
import java.util.List;

/**
 *
 */
//------------------------------------ Solution 1 -----------------------------------
public class CombinationSum {
    /**
     * Choose-No choose solution - brute force solution is to create deepcopu at each choose not choose
     * level and as target reaches zero use the path coming from the recursion call.
     * This adds on time and space complexities both to perform deep copy at each case. To improve this
     * using backtracking we can use the same path list throughout the process and add candidate to the same
     * path - make recursive call - remove same element (backtrack). Since now we are using the same path
     * throughout with backtracking we have to perform deep copy when we find our resultant path while adding to
     * result list, without this our final answer will contain empty lists.
     *
     * TC: O(2^(n+target)) n+target is worst case/max depth of choose-no-choose tree where n = number of candidates
     * Auxiliary SC: O(1) using the same path throughout
     * Recursive stack SC: same as TC - max depth of the tree.
     *
     * NOTE: order of choose no choose can be reversed and it will still work
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        helper(candidates, target, 0, result, new ArrayList<>());
        return result;
    }

    private void helper(int[] candidates, int target, int idx, List<List<Integer>> result, List<Integer> path) {
        //base
        if (target < 0) {
            return;
        }
        if (idx == candidates.length) {
            return;
        }
        /* this can be checked before or after idx and length check and it works because only
        in the choose scenario target reduces and we do not increment idx in this case as repeats
        of the same candidate is allowed, so idx doesn't go out of bound when target =0 */
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        //logic
        //not choose
        helper(candidates, target, idx + 1, result, path);

        //choose
        //action
        path.add(candidates[idx]);
        //recurse
        helper(candidates, target - candidates[idx], idx, result, path);
        //backtrack
        path.remove(path.size() - 1);
    }
}

//------------------------------------ Solution 2 -----------------------------------
class CombinationSum2 {
    /**
     * for loop recursive solution - we take two pointers pivot and i (inside the recursion) we start our for loop with
     * pivot as only unique combinations are allowed we dont want to start i at 0 which will give us all permutations of the
     * solution. With this setup you revisit the same i making recursive call since repeated elemets are allowed. Combine this with
     * backtracking to optimize on TC/SC by eliminating need of deep copy at each recursive call.
     *
     * TC and SC both are Same as previous solution as we are indirectly doing choose no choose only when keep i at same place or incrementing
     * i once base case is hit - this makes it no choose case
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        helper(candidates, target, 0, result, new ArrayList<>());
        return result;
    }

    private void helper(int[] candidates, int target, int pivot, List<List<Integer>> result, List<Integer> path) {
        //base
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if(target < 0) { // no need for bound check on pivot as i is within for loop always and our recursion also calls i only again
            return;
        }

        //logic
        for (int i = pivot; i < candidates.length; i++) {
            //action
            path.add(candidates[i]);
            //recurse
            helper(candidates, target - candidates[i], i, result, path);
            //backtrack
            path.remove(path.size() - 1);
        }
    }
}