package vn.hibernateknowledge.hibernateorm.representation;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@ExtendWith(MockitoExtension.class)
class ManagementControllerTest {
    
    @Mock
    Controller managementController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveManagemenet_normalManagement_saveManagementSuccessfully() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(managementController)
                   .saveManagemenet();
        
        managementController.saveManagemenet();

        verify(managementController, times(1)).saveManagemenet();
    }

}
