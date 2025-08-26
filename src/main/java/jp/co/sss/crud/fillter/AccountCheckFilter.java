package jp.co.sss.crud.fillter;


import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
@Component 

public class AccountCheckFilter extends HttpFilter {
	 @Override 
     public void doFilter( 
          HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
             throws IOException, ServletException { 
         System.out.println("Done Message01Filter"); 
         chain.doFilter(request, response); 
     } 

}
