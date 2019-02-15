package com.mb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mb.dto.SpendRequestDTO;
import com.mb.service.BlockingMessageService;
import com.mb.service.TransactionService;
import com.mb.service.TxnAccountService;
import com.mb.service.UserService;
import com.mb.util.DtoUtil;

import ratpack.exec.Blocking;
import ratpack.exec.Promise;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.http.Request;
import ratpack.jackson.Jackson;
import ratpack.jackson.JsonRender;
import ratpack.spring.config.EnableRatpack;

@SpringBootApplication
@EnableRatpack
public class App {


	@Bean
	Action<Chain> restAPIHanler() {
		return chain -> chain
		.get(ctx -> ctx.render("Hello!"))
		.get("json", ctx -> {
			BlockingMessageService messageService = ctx.get(BlockingMessageService.class);
			Promise<String> promise = Blocking.get(messageService::send);
			promise.then(ctx::render);

		}).post("login", ctx -> {
			UserService userService = ctx.get(UserService.class);
			ctx.render(Jackson.json(userService.createBalanceDTO()));
		}).get("balance", ctx -> {
			final Request request = ctx.getRequest();
			String token = request.getHeaders().get("token");
			TxnAccountService txnAccountService = ctx.get(TxnAccountService.class);
			ctx.render(Jackson.json(txnAccountService.getAccBalanceDTO(token)));
		}).post("spend",ctx-> {
				ctx.parse(SpendRequestDTO.class).then(spendRequest -> {
				final Request request = ctx.getRequest();
				String token = request.getHeaders().get("token");
				TransactionService transactionService = ctx.get(TransactionService.class);
				ctx.render(Jackson.json(transactionService.spend(token, spendRequest)));
			});
		}).get("transactions",ctx -> {
			final Request request = ctx.getRequest();
			String token = request.getHeaders().get("token");
			TransactionService transactionService = ctx.get(TransactionService.class);
			ctx.render(Jackson.json(DtoUtil.createTxnDtoList(transactionService.getAllTransaction(token))));
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
