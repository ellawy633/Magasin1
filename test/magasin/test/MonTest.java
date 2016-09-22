/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasin.test;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.text.html.HTML;
import magasin.entity.Categorie;
import magasin.entity.Client;
import magasin.entity.Commande;
import magasin.entity.Produit;
import org.eclipse.persistence.sessions.coordination.Command;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author tom
 */
public class MonTest {

    @Before
    public void avant() {
        // Vide toutes les tables
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM Commande c").executeUpdate();
        em.createQuery("DELETE FROM Client c").executeUpdate();
        em.createQuery("DELETE FROM Produit p").executeUpdate();
        em.createQuery("DELETE FROM Categorie p").executeUpdate();

        // Ajoutes des données en spécifiant les IDs que l'on va récup ds les tests unitaires
        // Persister en bases certaines données
        Categorie c1 = new Categorie();
        c1.setId(1L);
        c1.setNom("Basket");
        em.persist(c1);
        Categorie c2 = new Categorie();
        c2.setId(2L);
        c2.setNom("Lunettes solaires");
        em.persist(c2);

        Produit rayBan = new Produit();
        rayBan.setId(1L);
        rayBan.setCategorie(c2);
        c2.getProduits().add(rayBan);
        em.persist(rayBan);

        Client Riri = new Client();
        Riri.setId(1L);
        Riri.setNom("Riri");
        em.persist(Riri);

        Client Fifi = new Client();
        Fifi.setId(2L);
        Fifi.setNom("Fifi");
        em.persist(Fifi);

        Client Loulou = new Client();
        Loulou.setId(3L);
        Loulou.setNom("Loulou");
        em.persist(Loulou);

        Commande cm1 = new Commande();
        cm1.setClient(Riri);
        cm1.setId(1L);
        cm1.setPrix(1000);
        Riri.getCommandes().add(cm1);
        em.persist(cm1);

        Commande cm2 = new Commande();
        cm2.setClient(Loulou);
        cm2.setId(2L);
        Loulou.getCommandes().add(cm2);
        cm2.setPrix(5);

        em.persist(cm2);

        Commande cm3 = new Commande();
        cm3.setClient(Loulou);
        cm3.setId(3L);
        Loulou.getCommandes().add(cm3);
        cm3.setPrix(2);
        em.persist(cm3);

        em.getTransaction().commit();
    }

    @Test
    public void verifiQueCmd2PassParRiriKo() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Commande cmd = em.find(Commande.class, 2L);
        Assert.assertNotEquals("Riri", cmd.getClient().getNom());

    }

    @Test
    public void verifiQueCmd3PassParLoulououOk() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        // String nomCliCom3 = em.find(Commande.class, 3L).getClient().getNom();
        // if( nomCliCom3.equals("Loulou") == false ) 
        //    Assert.fail();
        Commande cmd = em.find(Commande.class, 3L);
        Assert.assertEquals("Loulou", cmd.getClient().getNom());

    }

    @Test
    public void verifiQueNbrCmdLoulouEst2() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Client c = em.find(Client.class, 3L);
        if (c.getCommandes().size() != 2) {
            Assert.fail();
        }
    }

    @Test
    public void verifieQueCatId1EstBasket() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Categorie cat = em.find(Categorie.class, 1L);

        if (cat.getNom().equals("Basket") == false) {
            Assert.fail("CA MARCHE PAS MON GARS!");
        }
    }

    @Test
    public void testListeProdCategorie() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Categorie cat = em.find(Categorie.class, 1L);
        for (Produit p : cat.getProduits()) {

            System.out.println(p);
        }
    }

    @Test
    public void testCreateDB() {

    }

}
