<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>	
<jsp:include page="../template/head.jsp" /> 
</head>
<body class="index-page">
	<!-- Header -->
	<header id="header" class="header d-flex align-items-center fixed-top">
		<jsp:include page="../template/header.jsp" />
		
	</header>
	<main class="main">
		<!-- Carrusel -->
		<section id="hero-carousel" class="section mt-5 pt-4">
			<div class="container">
				<div id="eventCarousel" class="carousel slide"
					data-bs-ride="carousel">
					<div class="carousel-indicators">
						<button type="button" data-bs-target="#eventCarousel"
							data-bs-slide-to="0" class="active"></button>
						<button type="button" data-bs-target="#eventCarousel"
							data-bs-slide-to="1"></button>
					</div>
					<div class="carousel-inner">
						<!-- Conferencia -->
						<div class="carousel-item active">
							<img src="img/events/ConferenciaTecnologia.webp"
								class="d-block w-100 carousel-img"
								alt="Conferencia de Tecnología">
							<div class="carousel-caption d-none d-md-block">
								<h5 class="carousel-title">Conferencia de Tecnología</h5>
								<p class="carousel-text">Evento sobre innovación tecnológica</p>
								<a href="html/consultaEvento_EV01VVis.html"
									class="btn btn-primary btn-lg me-3">Ver ediciones</a>
							</div>
						</div>
						<!-- Maratón -->
						<div class="carousel-item">
							<img src="img/events/maraton-de-montevideo.jpg"
								class="d-block w-100 carousel-img" alt="Maratón de Montevideo">
							<div class="carousel-caption d-none d-md-block">
								<h5 class="carousel-title">Maratón de Montevideo</h5>
								<p class="carousel-text">Competencia deportiva anual en la
									capital</p>
								<a href="html/consultaEvento_EV04VVis.html"
									class="btn btn-primary btn-lg me-3">Ver ediciones</a>
							</div>
						</div>
						<button class="carousel-control-prev" type="button"
							data-bs-target="#eventCarousel" data-bs-slide="prev">
							<span class="carousel-control-prev-icon"></span>
						</button>
						<button class="carousel-control-next" type="button"
							data-bs-target="#eventCarousel" data-bs-slide="next">
							<span class="carousel-control-next-icon"></span>
						</button>
					</div>
				</div>
		</section>
		<!-- Eventos -->
		<section id="speakers" class="speakers section">
			<div class="container section-title">
				<h2>Eventos</h2>
			</div>
			<!-- Carrusel de Categorías -->
			<section id="categories-carousel">
				<div class="container">
					<div id="categoriesCarousel" class="carousel slide"
						data-bs-interval="false">
						<div class="carousel-inner">
							<div class="carousel-item active">
								<div class="d-flex flex-wrap gap-2 justify-content-center">
									<button type="button"
										class="btn btn-outline-primary category-btn"
										data-category="tecnologia">
										<i class="bi bi-cpu"></i> Tecnología
									</button>
									<button type="button"
										class="btn btn-outline-primary category-btn"
										data-category="innovacion">
										<i class="bi bi-lightbulb"></i> Innovación
									</button>
									<button type="button"
										class="btn btn-outline-primary category-btn"
										data-category="deporte">
										<i class="bi bi-trophy"></i> Deporte
									</button>
									<button type="button"
										class="btn btn-outline-primary category-btn"
										data-category="salud">
										<i class="bi bi-heart-pulse"></i> Salud
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<div class="container">
				<div class="row g-4 justify-content-center">
					<!-- EV01 -->
					<div class="col-lg-3 col-md-6">
						<a href="html/consultaEvento_EV01VVis.html">
							<div class="speaker-card" data-categories="tecnologia,innovacion">
								<div class="speaker-image">
									<img src="img/default.png" alt="Conferencia de Tecnología"
										class="img-fluid">
								</div>
								<div class="speaker-content">
									<p class="speaker-title">Conferencia de Tecnología</p>
									<p class="speaker-company">Evento sobre innovación
										tecnológica</p>
									<div class="categories">
										<a href="#" title="Tecnología"><i class="bi bi-cpu"></i></a> <a
											href="#" title="Innovación"><i class="bi bi-lightbulb"></i></a>
									</div>
									<a href="html/consultaEvento_EV01VVis.html"
										class="btn btn-primary btn-lg me-3">Ver ediciones</a>
								</div>
							</div>
						</a>
					</div>
					<!-- EV04 -->
					<div class="col-lg-3 col-md-6">
						<a href="html/consultaEvento_EV04VVis.html">
							<div class="speaker-card" data-categories="deporte,salud">
								<div class="speaker-image">
									<img src="img/events/IMG-EV04.png" alt="Maratón de Montevideo"
										class="img-fluid">
								</div>
								<div class="speaker-content">
									<p class="speaker-title">Maratón de Montevideo</p>
									<p class="speaker-company">Competencia deportiva anual en
										la capital</p>
									<div class="categories">
										<a href="#" title="Deporte"><i class="bi bi-trophy"></i></a> <a
											href="#" title="Salud"><i class="bi bi-heart-pulse"></i></a>
									</div>
									<a href="html/consultaEvento_EV04VVis.html"
										class="btn btn-primary btn-lg me-3">Ver ediciones</a>
								</div>
							</div>
						</a>
					</div>
				</div>	
			</div>
	</main>
	<!-- Footer -->
	<hr class="mt-5 mb-4"
		style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
	<footer id="footer" class="footer position-relative light-background">
	<jsp:include page="../template/footer.jsp" />
	</footer>
	<!-- Scroll Top -->
	<a href="#" id="scroll-top"
		class="scroll-top d-flex align-items-center justify-content-center">
		<i class="bi bi-arrow-up-short"></i>
	</a>
	<!-- Preloader -->
	<div id="preloader"></div>
	<!-- Bootstrap JS -->
	<script src="media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- Main JS -->
	<script src="media/js/main.js"></script>
</body>
</html>	