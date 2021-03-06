validarSession();

function validarSession() {
    $.ajax({
        url: '/livrare/UsuariosServlet?acao=validarsessao',
        method: "GET"
    }).done(function (retorno) {
        if (retorno === "false") {
            swal({
                title: "Alerta!",
                text: "Acesso Restrito!",
                icon: "warning",
                buttons: {
                    ok: "Ok"
                },
                dangerMode: true,
            }).then((value) => {
                window.location.href = "/livrare/jsp/login.jsp";
            });
        } else {
            listLivros();
            listAlunos();
        }
    });
}

$('#salvar').click(function () {
    $.ajax({
        url: '/livrare/EmprestimosServlet',
        data: {id: $('#id').val(), aluno: $('#aluno').val(), livro: $('#livroUnidade').val(), anode: $('#anoDe').val(), anoate: $('#anoAte').val(), status: $('#status').val(), acao: 'salvar'}
    }).done(function (retorno) {
        if (retorno == "true" && $('#id').val() == '') {
            swal({
                title: "Sucesso!",
                text: "Emprestimo cadastrado com sucesso!",
                icon: "success",
                buttons: {
                    listagem: {
                        text: "Ir para listagem",
                        className: "btn-secondary"
                    },
                    ok: "Ok"
                },
                dangerMode: true,
            }).then((value) => {
                if (value == 'listagem') {
                    window.location.href = "listemprestimos.jsp";
                } else {
                    limparCampos();
                }
            });
        } else if (retorno == "true" && $('#id').val() != '') {
            swal({
                title: "Sucesso!",
                text: "Emprestimo atualizado com sucesso!",
                icon: "success",
                buttons: {
                    listagem: {
                        text: "Ir para listagem",
                        className: "btn-secondary"
                    },
                    ok: "Ok"
                },
                dangerMode: true,
            }).then((value) => {
                if (value == 'listagem') {
                    window.location.href = "listemprestimos.jsp";
                } else {
                    limparCampos();
                }
            });
        } else {
            swal("Erro!", "Algo de errado aconteceu: " + retorno, "error");
        }
    });
});

function limparCampos() {
    $('input').val('');
}

function listLivros() {
    $.ajax({
        url: '/livrare/LivrosDidaticosServlet',
        data: {acao: "select"}
    }).done(function (retorno) {
        $('#selectLivros').html(retorno);
    });
}

function listAlunos() {
    $.ajax({
        url: '/livrare/AlunosServlet',
        data: {acao: "select"}
    }).done(function (retorno) {
        $('#selectAlunos').html(retorno);
    });
}

function atualizarUnidades() {
    $.ajax({
        url: '/livrare/LivrosUnidadesServlet',
        data: {acao: "select", id: $('#livro').val()}
    }).done(function (retorno) {
        $('#selectUnidades').html(retorno);
    });
}