package createmovementstest;

import com.bankmock.domain.model.createbankingmovement.IExternalBankConsumer;
import com.bankmock.domain.model.createbankingmovement.bankAccount.IBankAccountGateway;
import com.bankmock.domain.model.createtoken.ITokenGateway;
import com.bankmock.domain.usecase.createbankingmovement.createdebit.DebitValidator;
import com.bankmock.domain.usecase.createbankingmovement.creditcreate.CreditCreator;
import com.bankmock.domain.usecase.createbankingmovement.DebitMovement;
import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DebitValidatorTest {

    @Mock
    IBankAccountGateway iBankAccountGateway;
    @Mock
    ITokenGateway iTokenGateway;
    @Mock
    IExternalBankConsumer iExternalBankConsumer;
    @Mock
    CreditCreator creditCreator;
    @Mock
    DebitMovement debitMovement;

    @InjectMocks
    private DebitValidator debitValidator;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

    }

/*
    @Test
    void testValidateDifferentBank_Success() {
        // Configurar comportamiento del mock para notificación exitosa
        when(iExternalBankConsumer.notifyBank()).thenReturn(true);

        // Ejecutar el método
        bankingService.validateDifferentBank(new BigDecimal("100.00"), 1L);

        // Verificar que los métodos relacionados con fallos no se llamaron
        verify(iExternalBankConsumer, times(1)).notifyBank();
        verify(iBankAccountGateway, never()).findAccountById(anyLong());
        verify(creditMovement, never()).creditAccount(any(BigDecimal.class), any(BankAccount.class));
    }

    @Test
    void testValidateDifferentBank_NotificationFailure() {
        // Configurar comportamiento del mock para notificación fallida
        when(iExternalBankConsumer.notifyBank()).thenReturn(false);

        // Configurar respuesta del método findAccountById
        BankAccount mockAccount = new BankAccount(1L, "12345678", new BigDecimal("500.00"));
        when(iBankAccountGateway.findAccountById(1L)).thenReturn(mockAccount);

        // Ejecutar y verificar excepción
        assertThrows(AppException.class, () ->
                bankingService.validateDifferentBank(new BigDecimal("100.00"), 1L)
        );

        // Verificar interacciones
        verify(iExternalBankConsumer, times(1)).notifyBank();
        verify(iBankAccountGateway, times(1)).findAccountById(1L);
        verify(creditMovement, times(1)).creditAccount(new BigDecimal("100.00"), mockAccount);
    }

    //----------------------------------------------------------------------------------------------------------//

    @Test
    void testValidateSameBank() {

        when(iTokenGateway.findByToken("")).thenReturn(null);
        when(iBankAccountGateway.findAccountById(null)).thenReturn(null);
        doNothing().when(creditMovement).creditAccount(any(BigDecimal.class), any(BankAccount.class));

        // Ejecutar el método
        bankingService.validateSameBank("", null);

        // Verificar que los métodos relacionados con fallos no se llamaron
        verify(iTokenGateway, times(1)).findByToken("");
        verify(iBankAccountGateway, times(1)).findAccountById(null);
        verify(creditMovement, times(1)).creditAccount(new BigDecimal("100.00"), null);

        //verify(iBankAccountGateway, never()).findAccountById(anyLong());
        //verify(creditMovement, never()).creditAccount(any(BigDecimal.class), any(BankAccount.class));
    }

    @Test
    void testValidateToken() {

        TokenAccount mockTokenAccount = new TokenAccount("sampleToken", "commercialAlly1");
        //debe retornar un token
        when(iTokenGateway.findByToken("")).thenReturn(null);

        // Ejecutar el método
        TokenAccount token = bankingService.validateToken("", "");


        // Verificar resultado
        assertNotNull(token);
        assertEquals("sampleToken", token.getToken());
        assertEquals("commercialAlly1", token.getCommercialAlly());
        // Verificar que los métodos relacionados con fallos no se llamaron
        verify(iTokenGateway, times(1)).findByToken("");

    }*/
}
