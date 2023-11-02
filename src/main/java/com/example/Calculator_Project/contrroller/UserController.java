package com.example.Calculator_Project.contrroller;

import com.example.Calculator_Project.constant.Pages;
import com.example.Calculator_Project.constant.RequestRouting;
import com.example.Calculator_Project.model.Calculations;
import com.example.Calculator_Project.model.form.Equation;
import com.example.Calculator_Project.model.form.RegistrationRequest;
import com.example.Calculator_Project.repository.CalculationsRepository;
import com.example.Calculator_Project.util.GreetingUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BinaryOperator;

@Controller
@AllArgsConstructor
@RequestMapping(RequestRouting.User.ROUTE)
public class UserController extends BaseController{
    private final CalculationsRepository calculationsRepository;

    /**
     *
     * @param equation form to be filled in
     * @param model {@link Model} spring mvc model
     * @return html
     */
    @GetMapping(RequestRouting.User.HOME)
    public ModelAndView userHome(Equation equation, Model model) {


        ModelAndView goo = new ModelAndView(Pages.User.ROUTE);
        model.addAttribute("greeting", GreetingUtil.getGreeting());
        model.addAttribute("equation",equation);


        model.addAttribute("userProfile", getLoggedUser());


        return goo;
    }

    /**
     *
     * @param equation form to be filled in
     * @param model {@link Model} spring mvc model
     * @return html
     */
    @PostMapping(RequestRouting.User.HOME)
    public String Calculate(@Valid @ModelAttribute("equation") Equation equation, BindingResult result, Model model) {
        model.addAttribute("greeting", GreetingUtil.getGreeting());
        model.addAttribute("equation",equation);


        model.addAttribute("userProfile", getLoggedUser());

        if (result.hasErrors()) {
            return Pages.User.ROUTE ;
        }

        String expression = equation.getEquation();

        Map<String, Double> constants = new HashMap<>();
        constants.put("pi", Math.PI);
        constants.put("e", Math.E);


        try {
            double output = evaluateExpression(expression, constants);

            model.addAttribute("output",output);
            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();

            // Format the date and time (optional)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            Calculations calculations = new Calculations(equation.getEquation(),
                    output, formattedDateTime, getLoggedUser());
            calculationsRepository.save(calculations);

            System.out.println("Current Date and Time: " + formattedDateTime);
        } catch (Exception e) {
            result.rejectValue("equation", "error.equation", "Error evaluating expression");
            return Pages.User.ROUTE ;
        }


        return Pages.User.RESULT;
    }


    /**
     *
     * @return html
     */
    @GetMapping(RequestRouting.User.VIEW_ALL_CALCULATIONS)
    public ModelAndView getUserAllCalculations(Model model){


        ModelAndView goo = new ModelAndView( Pages.User.VIEW_ALL_CALCULATIONS);
        model.addAttribute("greeting", GreetingUtil.getGreeting());


        model.addAttribute("userProfile", getLoggedUser());


        model.addAttribute("calculationsList", calculationsRepository.findAppUserById(getLoggedUser()));



        return goo;


    }





    public static double evaluateExpression(String expression, Map<String, Double> constants) {
        expression = expression.replaceAll("\\s+", ""); // Remove whitespace
        return evaluate(expression, constants);
    }

    public static double evaluate(String expression, Map<String, Double> constants) {
        List<String> outputQueue = new ArrayList<>();
        Deque<String> operatorStack = new LinkedList<>();
        expression = expression.replaceAll("root", "root_"); // Avoid conflicts with other operators

        String[] tokens = expression.split("(?<=[-+*/^()])|(?=[-+*/^()])");

        for (String token : tokens) {
            if (isNumeric(token)) {
                outputQueue.add(token);
            } else if (constants.containsKey(token)) {
                outputQueue.add(constants.get(token).toString());
            } else if (isFunction(token)) {
                operatorStack.push(token);
            } else if (isOperator(token)) {
                while (!operatorStack.isEmpty() && isOperator(operatorStack.peek())
                        && hasHigherPrecedence(token, operatorStack.peek())) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.pop(); // Pop the "("
                if (!operatorStack.isEmpty() && isFunction(operatorStack.peek())) {
                    outputQueue.add(operatorStack.pop());
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }

        return evaluateRPN(outputQueue, constants);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOperator(String str) {
        return "+-*/^".contains(str);
    }

    public static boolean isFunction(String str) {
        return str.startsWith("root_");
    }

    public static int getPrecedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
        }
        return 0;
    }

    public static boolean hasHigherPrecedence(String op1, String op2) {
        int precedence1 = getPrecedence(op1);
        int precedence2 = getPrecedence(op2);
        return precedence1 > precedence2;
    }

    public static double evaluateRPN(List<String> rpnExpression, Map<String, Double> constants) {
        Deque<Double> stack = new LinkedList<>();

        for (String token : rpnExpression) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (constants.containsKey(token)) {
                stack.push(constants.get(token));
            } else if (isFunction(token)) {
                String functionName = token.replace("root_", "");
                double y = stack.pop();
                double x = stack.pop();
                double result = evaluateFunction(functionName, x, y);
                stack.push(result);
            } else if (isOperator(token)) {
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                BinaryOperator<Double> operator = getOperator(token);
                double result = operator.apply(operand1, operand2);
                stack.push(result);
            }
        }

        return stack.pop();
    }

    public static double evaluateFunction(String functionName, double x, double y) {
        switch (functionName) {
            case "root_2":
                return Math.pow(x, 1.0 / y);
            case "root_3":
                return Math.pow(x, 1.0 / 3);
            default:
                throw new IllegalArgumentException("Unknown function: " + functionName);
        }
    }

    public static BinaryOperator<Double> getOperator(String operator) {
        switch (operator) {
            case "+":
                return Double::sum;
            case "-":
                return (x, y) -> x - y;
            case "*":
                return (x, y) -> x * y;
            case "/":
                return (x, y) -> x / y;
            case "^":
                return Math::pow;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}

