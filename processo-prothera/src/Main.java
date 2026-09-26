import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.Period;
import java.util.Comparator;
import java.math.RoundingMode;

private static void mostraFuncionarios(List<Funcionario> listaFuncionarios, DateTimeFormatter formatadorData, DecimalFormatSymbols simbolos, DecimalFormat formatadorSalario){

    for(Funcionario f : listaFuncionarios) {
        String dataFormatada = f.getDataNascimento().format(formatadorData);
        String salarioFormatado = formatadorSalario.format(f.getSalario());

        System.out.println("Nome: " + f.getNome()
                + " | Data Nascimento: " + dataFormatada
                + " | Salário: R$ " + salarioFormatado
                + " | Função: " + f.getFuncao()
        );
    }

    System.out.println("---------------------------------------------------------------------------------------------");
}

void main() {
    List<Funcionario> listaFuncionarios = new ArrayList<>();

    listaFuncionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
    listaFuncionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
    listaFuncionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
    listaFuncionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
    listaFuncionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
    listaFuncionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
    listaFuncionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
    listaFuncionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
    listaFuncionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
    listaFuncionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

    listaFuncionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

    DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DecimalFormatSymbols simbolos = new DecimalFormatSymbols(Locale.of("pt", "BR"));
    DecimalFormat formatadorSalario = new DecimalFormat("#,##0.00", simbolos);

    mostraFuncionarios(listaFuncionarios, formatadorData, simbolos, formatadorSalario);

    for (Funcionario f : listaFuncionarios){
        f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")));
    }

    System.out.println("------------------------------------Aumento de salário---------------------------------------");

    mostraFuncionarios(listaFuncionarios, formatadorData, simbolos, formatadorSalario);

    System.out.println("--------------------------------------Utilizando Map-----------------------------------------");

    Map<String, List<Funcionario>> funcionariosPorFuncao = listaFuncionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()){
        System.out.println("Função: " + entry.getKey());
        for (Funcionario f : entry.getValue()){
            System.out.println("   - " + f.getNome());
        }
    }

    System.out.println("---------------------------------------------------------------------------------------------");
    System.out.println("-------------------------------Funcionarios por aniversário----------------------------------");

    for (Funcionario f : listaFuncionarios) {
        int mes = f.getDataNascimento().getMonthValue();
        if (mes == 10 || mes == 12) {
            System.out.println("Nome: " + f.getNome() +
                    " | Data Nascimento " + f.getDataNascimento().format(formatadorData));
        }
    }

    System.out.println("---------------------------------------------------------------------------------------------");
    System.out.println("----------------------------------------Mais Velho-------------------------------------------");


    Funcionario maisVelho = listaFuncionarios.getFirst();
    for (Funcionario f : listaFuncionarios) {
        if (f.getDataNascimento().isBefore(maisVelho.getDataNascimento())) maisVelho = f;
    }
    int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
    System.out.println("Nome: " + maisVelho.getNome()
            + "  |  Idade: " + idade + " anos"
    );

    System.out.println("---------------------------------------------------------------------------------------------");
    System.out.println("-------------------------------------Ordem Alfabetica----------------------------------------");

    List<Funcionario> funcionariosOrdenados = new ArrayList<>(listaFuncionarios);
    funcionariosOrdenados.sort(Comparator.comparing(Funcionario::getNome));
    mostraFuncionarios(funcionariosOrdenados, formatadorData, simbolos, formatadorSalario);

    System.out.println("------------------------------------Total dos Salários---------------------------------------");

    BigDecimal totalSalarios = BigDecimal.ZERO;
    for (Funcionario f : listaFuncionarios) {
        totalSalarios = totalSalarios.add(f.getSalario());
    }
    System.out.println("Total dos salários: R$ " + formatadorSalario.format(totalSalarios));

    System.out.println("---------------------------------------------------------------------------------------------");
    System.out.println("--------------------------------Salários mínimos por funcionário-----------------------------");

    BigDecimal salarioMinimo = new BigDecimal("1212.00");
    for (Funcionario f : listaFuncionarios) {
        BigDecimal qtdSalariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
        System.out.println("Nome: " + f.getNome() + " | Ganha: " + qtdSalariosMinimos + " salários mínimos");
    }
}
