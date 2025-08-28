package jp.co.sss.crud.fillter;

import jakarta.servlet.http.HttpFilter;

//import java.io.IOException;
//
//import org.springframework.stereotype.Component;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//@Component
public class LoginCheckFilter extends HttpFilter{
//	@Override
//    public void doFilter(
//      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//      throws IOException, ServletException {
//        // リクエストURLを取得
//        String requestURL = request.getRequestURI();
//        if (requestURL.endsWith("/loginWithValidation")) {
//            //リクエスト URL が「ログイン画面への遷移処理」、
//            //「ログイン処理」宛ての場合、ログインチェックを実施せず、
//            //リクエスト対象のコントローラの処理に移る
//            chain.doFilter(request, response);
//        } else {
//            //セッション情報を取得
//            HttpSession session = request.getSession();
//          //セッション情報からユーザのログイン情報(セッション属性 userId)を取得
//            Integer userId = (Integer) session.getAttribute("userId");
//
//            if (userId == null) {
//                //ログイン情報が存在しない場合（ログイン情報(userId) が null の場合）、
//                //ログイン画面にリダイレクトする
//                response.sendRedirect("/shop/loginWithValidation");
//                return;
//            }else {
//                chain.doFilter(request, response);
//            }
//        }
//    }
}