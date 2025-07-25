package com.bruno.cadastrocep.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LogConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cepConsultado;

    @Column(columnDefinition = "TEXT")
    private String respostaApi;

    private LocalDateTime horarioConsulta;

    public LogConsulta(Long id, String cepConsultado, String respostaApi, LocalDateTime horarioConsulta) {
        this.id = id;
        this.cepConsultado = cepConsultado;
        this.respostaApi = respostaApi;
        this.horarioConsulta = horarioConsulta;
    }

    public LogConsulta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCepConsultado() {
        return cepConsultado;
    }

    public void setCepConsultado(String cepConsultado) {
        this.cepConsultado = cepConsultado;
    }

    public String getRespostaApi() {
        return respostaApi;
    }

    public void setRespostaApi(String respostaApi) {
        this.respostaApi = respostaApi;
    }

    public LocalDateTime getHorarioConsulta() {
        return horarioConsulta;
    }

    public void setHorarioConsulta(LocalDateTime horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }

    @Override
    public String toString() {
        return "LogConsulta [id=" + id + ", cepConsultado=" + cepConsultado + ", respostaApi=" + respostaApi
                + ", horarioConsulta=" + horarioConsulta + ", getCepConsultado()=" + getCepConsultado()
                + ", getHorarioConsulta()=" + getHorarioConsulta() + ", getId()=" + getId() + ", getRespostaApi()="
                + getRespostaApi() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
                + super.toString() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LogConsulta other = (LogConsulta) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (cepConsultado == null) {
            if (other.cepConsultado != null)
                return false;
        } else if (!cepConsultado.equals(other.cepConsultado))
            return false;
        if (respostaApi == null) {
            if (other.respostaApi != null)
                return false;
        } else if (!respostaApi.equals(other.respostaApi))
            return false;
        if (horarioConsulta == null) {
            if (other.horarioConsulta != null)
                return false;
        } else if (!horarioConsulta.equals(other.horarioConsulta))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((cepConsultado == null) ? 0 : cepConsultado.hashCode());
        result = prime * result + ((respostaApi == null) ? 0 : respostaApi.hashCode());
        result = prime * result + ((horarioConsulta == null) ? 0 : horarioConsulta.hashCode());
        return result;
    }
}
