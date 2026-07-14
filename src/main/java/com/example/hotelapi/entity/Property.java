@Entity
@Table(name = "properties") // Cədvəlin adı properties olaraq dəyişdi
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // YENİ ƏLAVƏ EDİLƏN SÜTUN (Məsələn: "HOTEL", "APARTMENT", "VILLA")
    @Column(name = "property_type", nullable = false)
    private String propertyType;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    // ... (Köhnə Getter və Setter-lər eyni qalır)

    // Yeni propertyType üçün Getter və Setter əlavə etməyi unutma!
    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }
}