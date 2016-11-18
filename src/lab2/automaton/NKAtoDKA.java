//package lab2.automaton;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by CHOPPER on 11/17/2016.
// */
//public class NKAtoDKA {
//
//	/**
//     *
//     */
//    private LinkedHashMap<String, LinkedHashMap<Character, Set<String>>> mapaEpsilonPrijelaza;
//    private List<String> epsilon;
//
//    public NKAtoDKA (LinkedHashMap<String, LinkedHashMap<Character, String>> mapaPrijelaza){
//        this.mapaPrijelaza = mapaPrijelaza;
//    }
//
//    public  void pretvori(){
//        LinkedHashMap<Character, List<String>> stateValues;
//        List<String> rezultat;
//        for(String state : mapaEpsilonPrijelaza.keySet()){
//            stateValues=mapaEpsilonPrijelaza.get(state);
//            List<String> epsiloni = epsilonProsiri(state);
//            for(Character temp: stateValues.keySet()){
//                for(String state2: epsiloni){
//                    LinkedHashMap<Character, List<String>> stateValues2 = mapaEpsilonPrijelaza.get(state2);
//                    for(Character temp2: stateValues2.keySet()){
//                        if(temp2.equals(temp) && !rezultat.contains(stateValues.get(temp))){
//                            rezultat.addAll(stateValues.get(temp));
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//    public List<String> epsilonProsiri(String state) {
//        LinkedHashMap<Character, List<String>> stateValues;
//            stateValues=mapaEpsilonPrijelaza.get(state);
//            for(Character temp: stateValues.keySet()){
//                if(temp.equals('$') && !epsilon.contains(stateValues.get(temp))){
//                    epsilon.addAll(stateValues.get(temp));
//                }
//            }
//            boolean gotovo=false;
//            while(!gotovo) {
//                for (String temp : epsilon) {
//                    for (Character temp2 : stateValues.keySet()) {
//                        if (temp2.equals('$') && !epsilon.contains(stateValues.get(temp))) {
//                            epsilon.addAll(stateValues.get(temp));
//                            gotovo = false;
//                        }
//                    }
//                }
//            }
//
//        return epsilon;
//    }
//
//}
