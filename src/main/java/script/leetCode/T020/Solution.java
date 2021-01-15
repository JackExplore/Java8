package script.leetCode.T020;

import java.util.Stack;

/**
 * ÓÐÐ§µÄÀ¨ºÅ
 */
public class Solution {

    public boolean isValid(String s){
        if("".equals(s)){
            return true;
        }

        Stack<Character> stack = new Stack<>();
        stack.push('?');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c){
                case '(':
                    stack.push(c);
                    break;
                case '[':
                    stack.push(c);
                    break;
                case '{':
                    stack.push(c);
                    break;

                case '}':{
                    if(!stack.pop().equals('{')){
                        return false;
                    }
                    break;
                }
                case ']':{
                    if(!stack.pop().equals('[')){
                        return false;
                    }
                    break;
                }
                case ')':{
                    if(!stack.pop().equals('(')){
                        return false;
                    }
                    break;
                }

            }

        }
        return stack.size() == 1;
    }
}
