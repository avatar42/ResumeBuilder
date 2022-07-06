package com.dea42.resume.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dea42.resume.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumeSoftwareTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeSoftware.class);
        ResumeSoftware resumeSoftware1 = new ResumeSoftware();
        resumeSoftware1.setId(1L);
        ResumeSoftware resumeSoftware2 = new ResumeSoftware();
        resumeSoftware2.setId(resumeSoftware1.getId());
        assertThat(resumeSoftware1).isEqualTo(resumeSoftware2);
        resumeSoftware2.setId(2L);
        assertThat(resumeSoftware1).isNotEqualTo(resumeSoftware2);
        resumeSoftware1.setId(null);
        assertThat(resumeSoftware1).isNotEqualTo(resumeSoftware2);
    }
}
