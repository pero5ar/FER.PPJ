import lab3.models.SemanticNode;
import lab3.semantic.InputParser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SemantickiAnalizatorTest {

    private String parseTree(String input) {
        List<String> inputList = new ArrayList<>();
        Collections.addAll(inputList, input.split("\n"));

        InputParser ip = new InputParser();

        inputList.forEach(ip::add);

        SemanticNode node = ip.parseTree();

        return node.fullTreeString();
    }

    @Test
    public void parseTreeTest() {
        // 02_broj
        String input = "<prijevodna_jedinica>\n" +
                " <vanjska_deklaracija>\n" +
                "  <definicija_funkcije>\n" +
                "   <ime_tipa>\n" +
                "    <specifikator_tipa>\n" +
                "     KR_INT 1 int\n" +
                "   IDN 1 main\n" +
                "   L_ZAGRADA 1 (\n" +
                "   KR_VOID 1 void\n" +
                "   D_ZAGRADA 1 )\n" +
                "   <slozena_naredba>\n" +
                "    L_VIT_ZAGRADA 1 {\n" +
                "    <lista_naredbi>\n" +
                "     <naredba>\n" +
                "      <naredba_skoka>\n" +
                "       KR_RETURN 2 return\n" +
                "       <izraz>\n" +
                "        <izraz_pridruzivanja>\n" +
                "         <log_ili_izraz>\n" +
                "          <log_i_izraz>\n" +
                "           <bin_ili_izraz>\n" +
                "            <bin_xili_izraz>\n" +
                "             <bin_i_izraz>\n" +
                "              <jednakosni_izraz>\n" +
                "               <odnosni_izraz>\n" +
                "                <aditivni_izraz>\n" +
                "                 <multiplikativni_izraz>\n" +
                "                  <cast_izraz>\n" +
                "                   <unarni_izraz>\n" +
                "                    <postfiks_izraz>\n" +
                "                     <primarni_izraz>\n" +
                "                      IDN 2 x\n" +
                "       TOCKAZAREZ 2 ;\n" +
                "    D_VIT_ZAGRADA 3 }\n";

        assertEquals(input, parseTree(input));
    }

    @Test
    public void parseTreeTest2() {
        // 17_log
        String input = "<prijevodna_jedinica>\n" +
                " <vanjska_deklaracija>\n" +
                "  <definicija_funkcije>\n" +
                "   <ime_tipa>\n" +
                "    <specifikator_tipa>\n" +
                "     KR_INT 1 int\n" +
                "   IDN 1 main\n" +
                "   L_ZAGRADA 1 (\n" +
                "   KR_VOID 1 void\n" +
                "   D_ZAGRADA 1 )\n" +
                "   <slozena_naredba>\n" +
                "    L_VIT_ZAGRADA 1 {\n" +
                "    <lista_deklaracija>\n" +
                "     <lista_deklaracija>\n" +
                "      <deklaracija>\n" +
                "       <ime_tipa>\n" +
                "        <specifikator_tipa>\n" +
                "         KR_INT 2 int\n" +
                "       <lista_init_deklaratora>\n" +
                "        <init_deklarator>\n" +
                "         <izravni_deklarator>\n" +
                "          IDN 2 y\n" +
                "         OP_PRIDRUZI 2 =\n" +
                "         <inicijalizator>\n" +
                "          <izraz_pridruzivanja>\n" +
                "           <log_ili_izraz>\n" +
                "            <log_i_izraz>\n" +
                "             <bin_ili_izraz>\n" +
                "              <bin_xili_izraz>\n" +
                "               <bin_i_izraz>\n" +
                "                <jednakosni_izraz>\n" +
                "                 <odnosni_izraz>\n" +
                "                  <aditivni_izraz>\n" +
                "                   <multiplikativni_izraz>\n" +
                "                    <cast_izraz>\n" +
                "                     <unarni_izraz>\n" +
                "                      <postfiks_izraz>\n" +
                "                       <primarni_izraz>\n" +
                "                        BROJ 2 12\n" +
                "       TOCKAZAREZ 2 ;\n" +
                "     <deklaracija>\n" +
                "      <ime_tipa>\n" +
                "       <specifikator_tipa>\n" +
                "        KR_INT 3 int\n" +
                "      <lista_init_deklaratora>\n" +
                "       <init_deklarator>\n" +
                "        <izravni_deklarator>\n" +
                "         IDN 3 x\n" +
                "        OP_PRIDRUZI 3 =\n" +
                "        <inicijalizator>\n" +
                "         <izraz_pridruzivanja>\n" +
                "          <log_ili_izraz>\n" +
                "           <log_ili_izraz>\n" +
                "            <log_i_izraz>\n" +
                "             <log_i_izraz>\n" +
                "              <bin_ili_izraz>\n" +
                "               <bin_xili_izraz>\n" +
                "                <bin_i_izraz>\n" +
                "                 <jednakosni_izraz>\n" +
                "                  <odnosni_izraz>\n" +
                "                   <aditivni_izraz>\n" +
                "                    <multiplikativni_izraz>\n" +
                "                     <cast_izraz>\n" +
                "                      <unarni_izraz>\n" +
                "                       <unarni_operator>\n" +
                "                        OP_NEG 3 !\n" +
                "                       <cast_izraz>\n" +
                "                        <unarni_izraz>\n" +
                "                         <postfiks_izraz>\n" +
                "                          <primarni_izraz>\n" +
                "                           BROJ 3 1\n" +
                "             OP_I 3 &&\n" +
                "             <bin_ili_izraz>\n" +
                "              <bin_xili_izraz>\n" +
                "               <bin_i_izraz>\n" +
                "                <jednakosni_izraz>\n" +
                "                 <odnosni_izraz>\n" +
                "                  <aditivni_izraz>\n" +
                "                   <multiplikativni_izraz>\n" +
                "                    <cast_izraz>\n" +
                "                     <unarni_izraz>\n" +
                "                      <postfiks_izraz>\n" +
                "                       <primarni_izraz>\n" +
                "                        IDN 3 y\n" +
                "           OP_ILI 3 ||\n" +
                "           <log_i_izraz>\n" +
                "            <bin_ili_izraz>\n" +
                "             <bin_xili_izraz>\n" +
                "              <bin_i_izraz>\n" +
                "               <jednakosni_izraz>\n" +
                "                <jednakosni_izraz>\n" +
                "                 <odnosni_izraz>\n" +
                "                  <aditivni_izraz>\n" +
                "                   <aditivni_izraz>\n" +
                "                    <multiplikativni_izraz>\n" +
                "                     <cast_izraz>\n" +
                "                      <unarni_izraz>\n" +
                "                       <postfiks_izraz>\n" +
                "                        <primarni_izraz>\n" +
                "                         IDN 3 x\n" +
                "                   PLUS 3 +\n" +
                "                   <multiplikativni_izraz>\n" +
                "                    <cast_izraz>\n" +
                "                     <unarni_izraz>\n" +
                "                      <postfiks_izraz>\n" +
                "                       <primarni_izraz>\n" +
                "                        BROJ 3 2\n" +
                "                OP_EQ 3 ==\n" +
                "                <odnosni_izraz>\n" +
                "                 <odnosni_izraz>\n" +
                "                  <odnosni_izraz>\n" +
                "                   <aditivni_izraz>\n" +
                "                    <multiplikativni_izraz>\n" +
                "                     <cast_izraz>\n" +
                "                      <unarni_izraz>\n" +
                "                       <postfiks_izraz>\n" +
                "                        <primarni_izraz>\n" +
                "                         BROJ 3 2\n" +
                "                  OP_LT 3 <\n" +
                "                  <aditivni_izraz>\n" +
                "                   <multiplikativni_izraz>\n" +
                "                    <cast_izraz>\n" +
                "                     <unarni_izraz>\n" +
                "                      <postfiks_izraz>\n" +
                "                       <primarni_izraz>\n" +
                "                        BROJ 3 3\n" +
                "                 OP_GTE 3 >=\n" +
                "                 <aditivni_izraz>\n" +
                "                  <multiplikativni_izraz>\n" +
                "                   <cast_izraz>\n" +
                "                    <unarni_izraz>\n" +
                "                     <postfiks_izraz>\n" +
                "                      <primarni_izraz>\n" +
                "                       BROJ 3 5\n" +
                "      TOCKAZAREZ 3 ;\n" +
                "    <lista_naredbi>\n" +
                "     <naredba>\n" +
                "      <naredba_skoka>\n" +
                "       KR_RETURN 4 return\n" +
                "       <izraz>\n" +
                "        <izraz_pridruzivanja>\n" +
                "         <log_ili_izraz>\n" +
                "          <log_i_izraz>\n" +
                "           <bin_ili_izraz>\n" +
                "            <bin_xili_izraz>\n" +
                "             <bin_i_izraz>\n" +
                "              <jednakosni_izraz>\n" +
                "               <jednakosni_izraz>\n" +
                "                <odnosni_izraz>\n" +
                "                 <aditivni_izraz>\n" +
                "                  <multiplikativni_izraz>\n" +
                "                   <cast_izraz>\n" +
                "                    <unarni_izraz>\n" +
                "                     <postfiks_izraz>\n" +
                "                      <primarni_izraz>\n" +
                "                       IDN 4 main\n" +
                "               OP_EQ 4 ==\n" +
                "               <odnosni_izraz>\n" +
                "                <aditivni_izraz>\n" +
                "                 <multiplikativni_izraz>\n" +
                "                  <cast_izraz>\n" +
                "                   <unarni_izraz>\n" +
                "                    <postfiks_izraz>\n" +
                "                     <primarni_izraz>\n" +
                "                      BROJ 4 1\n" +
                "       TOCKAZAREZ 4 ;\n" +
                "    D_VIT_ZAGRADA 5 }\n";

        assertEquals(input, parseTree(input));
    }

    @Test
    public void parseTreeTest3() {
        // 29_for
        String input = "<prijevodna_jedinica>\n" +
                " <vanjska_deklaracija>\n" +
                "  <definicija_funkcije>\n" +
                "   <ime_tipa>\n" +
                "    <specifikator_tipa>\n" +
                "     KR_INT 1 int\n" +
                "   IDN 1 main\n" +
                "   L_ZAGRADA 1 (\n" +
                "   KR_VOID 1 void\n" +
                "   D_ZAGRADA 1 )\n" +
                "   <slozena_naredba>\n" +
                "    L_VIT_ZAGRADA 1 {\n" +
                "    <lista_deklaracija>\n" +
                "     <deklaracija>\n" +
                "      <ime_tipa>\n" +
                "       <specifikator_tipa>\n" +
                "        KR_INT 2 int\n" +
                "      <lista_init_deklaratora>\n" +
                "       <lista_init_deklaratora>\n" +
                "        <init_deklarator>\n" +
                "         <izravni_deklarator>\n" +
                "          IDN 2 i\n" +
                "       ZAREZ 2 ,\n" +
                "       <init_deklarator>\n" +
                "        <izravni_deklarator>\n" +
                "         IDN 2 y\n" +
                "        OP_PRIDRUZI 2 =\n" +
                "        <inicijalizator>\n" +
                "         <izraz_pridruzivanja>\n" +
                "          <log_ili_izraz>\n" +
                "           <log_i_izraz>\n" +
                "            <bin_ili_izraz>\n" +
                "             <bin_xili_izraz>\n" +
                "              <bin_i_izraz>\n" +
                "               <jednakosni_izraz>\n" +
                "                <odnosni_izraz>\n" +
                "                 <aditivni_izraz>\n" +
                "                  <multiplikativni_izraz>\n" +
                "                   <cast_izraz>\n" +
                "                    <unarni_izraz>\n" +
                "                     <postfiks_izraz>\n" +
                "                      <primarni_izraz>\n" +
                "                       BROJ 2 0\n" +
                "      TOCKAZAREZ 2 ;\n" +
                "    <lista_naredbi>\n" +
                "     <lista_naredbi>\n" +
                "      <lista_naredbi>\n" +
                "       <naredba>\n" +
                "        <naredba_petlje>\n" +
                "         KR_FOR 3 for\n" +
                "         L_ZAGRADA 3 (\n" +
                "         <izraz_naredba>\n" +
                "          <izraz>\n" +
                "           <izraz_pridruzivanja>\n" +
                "            <postfiks_izraz>\n" +
                "             <primarni_izraz>\n" +
                "              IDN 3 i\n" +
                "            OP_PRIDRUZI 3 =\n" +
                "            <izraz_pridruzivanja>\n" +
                "             <log_ili_izraz>\n" +
                "              <log_i_izraz>\n" +
                "               <bin_ili_izraz>\n" +
                "                <bin_xili_izraz>\n" +
                "                 <bin_i_izraz>\n" +
                "                  <jednakosni_izraz>\n" +
                "                   <odnosni_izraz>\n" +
                "                    <aditivni_izraz>\n" +
                "                     <multiplikativni_izraz>\n" +
                "                      <cast_izraz>\n" +
                "                       <unarni_izraz>\n" +
                "                        <postfiks_izraz>\n" +
                "                         <primarni_izraz>\n" +
                "                          BROJ 3 0\n" +
                "          TOCKAZAREZ 3 ;\n" +
                "         <izraz_naredba>\n" +
                "          <izraz>\n" +
                "           <izraz_pridruzivanja>\n" +
                "            <log_ili_izraz>\n" +
                "             <log_i_izraz>\n" +
                "              <bin_ili_izraz>\n" +
                "               <bin_xili_izraz>\n" +
                "                <bin_i_izraz>\n" +
                "                 <jednakosni_izraz>\n" +
                "                  <odnosni_izraz>\n" +
                "                   <odnosni_izraz>\n" +
                "                    <aditivni_izraz>\n" +
                "                     <multiplikativni_izraz>\n" +
                "                      <cast_izraz>\n" +
                "                       <unarni_izraz>\n" +
                "                        <postfiks_izraz>\n" +
                "                         <primarni_izraz>\n" +
                "                          IDN 3 i\n" +
                "                   OP_LT 3 <\n" +
                "                   <aditivni_izraz>\n" +
                "                    <multiplikativni_izraz>\n" +
                "                     <cast_izraz>\n" +
                "                      <unarni_izraz>\n" +
                "                       <postfiks_izraz>\n" +
                "                        <primarni_izraz>\n" +
                "                         BROJ 3 10\n" +
                "          TOCKAZAREZ 3 ;\n" +
                "         <izraz>\n" +
                "          <izraz_pridruzivanja>\n" +
                "           <log_ili_izraz>\n" +
                "            <log_i_izraz>\n" +
                "             <bin_ili_izraz>\n" +
                "              <bin_xili_izraz>\n" +
                "               <bin_i_izraz>\n" +
                "                <jednakosni_izraz>\n" +
                "                 <odnosni_izraz>\n" +
                "                  <aditivni_izraz>\n" +
                "                   <multiplikativni_izraz>\n" +
                "                    <cast_izraz>\n" +
                "                     <unarni_izraz>\n" +
                "                      OP_INC 3 ++\n" +
                "                      <unarni_izraz>\n" +
                "                       <postfiks_izraz>\n" +
                "                        <primarni_izraz>\n" +
                "                         IDN 3 i\n" +
                "         D_ZAGRADA 3 )\n" +
                "         <naredba>\n" +
                "          <slozena_naredba>\n" +
                "           L_VIT_ZAGRADA 3 {\n" +
                "           <lista_naredbi>\n" +
                "            <lista_naredbi>\n" +
                "             <naredba>\n" +
                "              <izraz_naredba>\n" +
                "               <izraz>\n" +
                "                <izraz_pridruzivanja>\n" +
                "                 <postfiks_izraz>\n" +
                "                  <primarni_izraz>\n" +
                "                   IDN 4 i\n" +
                "                 OP_PRIDRUZI 4 =\n" +
                "                 <izraz_pridruzivanja>\n" +
                "                  <log_ili_izraz>\n" +
                "                   <log_i_izraz>\n" +
                "                    <bin_ili_izraz>\n" +
                "                     <bin_xili_izraz>\n" +
                "                      <bin_i_izraz>\n" +
                "                       <jednakosni_izraz>\n" +
                "                        <odnosni_izraz>\n" +
                "                         <aditivni_izraz>\n" +
                "                          <aditivni_izraz>\n" +
                "                           <multiplikativni_izraz>\n" +
                "                            <cast_izraz>\n" +
                "                             <unarni_izraz>\n" +
                "                              <postfiks_izraz>\n" +
                "                               <primarni_izraz>\n" +
                "                                IDN 4 i\n" +
                "                          PLUS 4 +\n" +
                "                          <multiplikativni_izraz>\n" +
                "                           <cast_izraz>\n" +
                "                            <unarni_izraz>\n" +
                "                             <postfiks_izraz>\n" +
                "                              <primarni_izraz>\n" +
                "                               BROJ 4 1\n" +
                "               TOCKAZAREZ 4 ;\n" +
                "            <naredba>\n" +
                "             <izraz_naredba>\n" +
                "              <izraz>\n" +
                "               <izraz_pridruzivanja>\n" +
                "                <postfiks_izraz>\n" +
                "                 <primarni_izraz>\n" +
                "                  IDN 5 y\n" +
                "                OP_PRIDRUZI 5 =\n" +
                "                <izraz_pridruzivanja>\n" +
                "                 <log_ili_izraz>\n" +
                "                  <log_i_izraz>\n" +
                "                   <bin_ili_izraz>\n" +
                "                    <bin_xili_izraz>\n" +
                "                     <bin_i_izraz>\n" +
                "                      <jednakosni_izraz>\n" +
                "                       <odnosni_izraz>\n" +
                "                        <aditivni_izraz>\n" +
                "                         <aditivni_izraz>\n" +
                "                          <multiplikativni_izraz>\n" +
                "                           <cast_izraz>\n" +
                "                            <unarni_izraz>\n" +
                "                             <postfiks_izraz>\n" +
                "                              <primarni_izraz>\n" +
                "                               IDN 5 y\n" +
                "                         PLUS 5 +\n" +
                "                         <multiplikativni_izraz>\n" +
                "                          <cast_izraz>\n" +
                "                           <unarni_izraz>\n" +
                "                            <postfiks_izraz>\n" +
                "                             <primarni_izraz>\n" +
                "                              BROJ 5 2\n" +
                "              TOCKAZAREZ 5 ;\n" +
                "           D_VIT_ZAGRADA 6 }\n" +
                "      <naredba>\n" +
                "       <naredba_petlje>\n" +
                "        KR_FOR 7 for\n" +
                "        L_ZAGRADA 7 (\n" +
                "        <izraz_naredba>\n" +
                "         <izraz>\n" +
                "          <izraz_pridruzivanja>\n" +
                "           <postfiks_izraz>\n" +
                "            <primarni_izraz>\n" +
                "             IDN 7 i\n" +
                "           OP_PRIDRUZI 7 =\n" +
                "           <izraz_pridruzivanja>\n" +
                "            <log_ili_izraz>\n" +
                "             <log_i_izraz>\n" +
                "              <bin_ili_izraz>\n" +
                "               <bin_xili_izraz>\n" +
                "                <bin_i_izraz>\n" +
                "                 <jednakosni_izraz>\n" +
                "                  <odnosni_izraz>\n" +
                "                   <aditivni_izraz>\n" +
                "                    <multiplikativni_izraz>\n" +
                "                     <cast_izraz>\n" +
                "                      <unarni_izraz>\n" +
                "                       <postfiks_izraz>\n" +
                "                        <primarni_izraz>\n" +
                "                         BROJ 7 0\n" +
                "         TOCKAZAREZ 7 ;\n" +
                "        <izraz_naredba>\n" +
                "         <izraz>\n" +
                "          <izraz_pridruzivanja>\n" +
                "           <log_ili_izraz>\n" +
                "            <log_i_izraz>\n" +
                "             <bin_ili_izraz>\n" +
                "              <bin_xili_izraz>\n" +
                "               <bin_i_izraz>\n" +
                "                <jednakosni_izraz>\n" +
                "                 <odnosni_izraz>\n" +
                "                  <aditivni_izraz>\n" +
                "                   <multiplikativni_izraz>\n" +
                "                    <cast_izraz>\n" +
                "                     <unarni_izraz>\n" +
                "                      <postfiks_izraz>\n" +
                "                       <primarni_izraz>\n" +
                "                        IDN 7 main\n" +
                "         TOCKAZAREZ 7 ;\n" +
                "        <izraz>\n" +
                "         <izraz_pridruzivanja>\n" +
                "          <log_ili_izraz>\n" +
                "           <log_i_izraz>\n" +
                "            <bin_ili_izraz>\n" +
                "             <bin_xili_izraz>\n" +
                "              <bin_i_izraz>\n" +
                "               <jednakosni_izraz>\n" +
                "                <odnosni_izraz>\n" +
                "                 <aditivni_izraz>\n" +
                "                  <multiplikativni_izraz>\n" +
                "                   <cast_izraz>\n" +
                "                    <unarni_izraz>\n" +
                "                     OP_INC 7 ++\n" +
                "                     <unarni_izraz>\n" +
                "                      <postfiks_izraz>\n" +
                "                       <primarni_izraz>\n" +
                "                        IDN 7 i\n" +
                "        D_ZAGRADA 7 )\n" +
                "        <naredba>\n" +
                "         <slozena_naredba>\n" +
                "          L_VIT_ZAGRADA 7 {\n" +
                "          <lista_naredbi>\n" +
                "           <naredba>\n" +
                "            <izraz_naredba>\n" +
                "             <izraz>\n" +
                "              <izraz_pridruzivanja>\n" +
                "               <postfiks_izraz>\n" +
                "                <primarni_izraz>\n" +
                "                 IDN 8 y\n" +
                "               OP_PRIDRUZI 8 =\n" +
                "               <izraz_pridruzivanja>\n" +
                "                <log_ili_izraz>\n" +
                "                 <log_i_izraz>\n" +
                "                  <bin_ili_izraz>\n" +
                "                   <bin_xili_izraz>\n" +
                "                    <bin_i_izraz>\n" +
                "                     <jednakosni_izraz>\n" +
                "                      <odnosni_izraz>\n" +
                "                       <aditivni_izraz>\n" +
                "                        <multiplikativni_izraz>\n" +
                "                         <cast_izraz>\n" +
                "                          <unarni_izraz>\n" +
                "                           <postfiks_izraz>\n" +
                "                            <primarni_izraz>\n" +
                "                             BROJ 8 1\n" +
                "             TOCKAZAREZ 8 ;\n" +
                "          D_VIT_ZAGRADA 9 }\n" +
                "     <naredba>\n" +
                "      <naredba_skoka>\n" +
                "       KR_RETURN 10 return\n" +
                "       <izraz>\n" +
                "        <izraz_pridruzivanja>\n" +
                "         <log_ili_izraz>\n" +
                "          <log_i_izraz>\n" +
                "           <bin_ili_izraz>\n" +
                "            <bin_xili_izraz>\n" +
                "             <bin_i_izraz>\n" +
                "              <jednakosni_izraz>\n" +
                "               <odnosni_izraz>\n" +
                "                <aditivni_izraz>\n" +
                "                 <multiplikativni_izraz>\n" +
                "                  <cast_izraz>\n" +
                "                   <unarni_izraz>\n" +
                "                    <postfiks_izraz>\n" +
                "                     <primarni_izraz>\n" +
                "                      IDN 10 y\n" +
                "       TOCKAZAREZ 10 ;\n" +
                "    D_VIT_ZAGRADA 11 }\n";

        assertEquals(input, parseTree(input));
    }
}