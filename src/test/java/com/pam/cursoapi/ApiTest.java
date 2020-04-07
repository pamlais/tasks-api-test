package com.pam.cursoapi;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void test() {
		RestAssured.given().log().all()
		//nao precisa por o caminho todo pq o resto da uri ta no beforeclass
		.when().get("todo")
		.then().log().all();
	}
	
	@Test
	public void deveretornartarefas() {
		RestAssured.given()
		.when().get("todo")
		.then().statusCode(200);
	}
	
	//teste para postar alguma coisa 
	@Test
	public void deveadicionartarefacomsucesso() {
		RestAssured.given().body("{\"task\": \"Teste via API\", \"dueDate\": \"2020-12-30\"}").contentType(ContentType.JSON)
		.when().post("/todo")
		//o log faz imprimir no console. normalmente nao precisa
		.then().log().all().statusCode(201);
	}
	
	@Test
	public void naodeveadicionartarefainvalida() {
		RestAssured.given().body("{\"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\"}").contentType(ContentType.JSON)
		.when().post("/todo")
		//o log faz imprimir no console. normalmente nao precisa
		.then().log().all().statusCode(400)
		//verifica o texto da msg do statuscode400
		.body("message", CoreMatchers.is("Due date must not be in past"));
	}

}

